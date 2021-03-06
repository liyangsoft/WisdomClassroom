package com.sc.lesa.mediashar.jlib.server

import android.util.Log
import com.example.io.ByteObjectOutputStream
import com.example.io.Writable
import com.example.widget.toByteArray

import com.sc.lesa.mediashar.jlib.io.DataPack

import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.LinkedBlockingQueue

abstract class SocketServerThread(var port: Int) : Thread(TAG) {

    companion object{
        val TAG = SocketServerThread::class.java.name
        fun buildVideoPack(writable: Writable):ByteArray{
            val pack = DataPack(DataPack.TYPE_VIDEO,writable.toByteArray())
            return pack.toByteArray()
        }

        fun buildVoicePack(writable:Writable):ByteArray{
            val pack = DataPack(DataPack.TYPE_VOICE,writable.toByteArray())
            return pack.toByteArray()
        }
    }

    lateinit var serverSocket: ServerSocket
    var exit = false
    var bufferListVideo: LinkedBlockingQueue<Writable> = LinkedBlockingQueue(100)
    var bufferListVoice: LinkedBlockingQueue<Writable> = LinkedBlockingQueue(100)
    lateinit var socket: Socket
    lateinit var socketout: OutputStream
    lateinit var dataOutput: ByteObjectOutputStream


    override fun run() {
        try {
            serverSocket = ServerSocket(port)
            serverSocket.soTimeout=3000
        }catch (t:Throwable){
            t.printStackTrace()
            //ε€ηθΏε
            onError(t)
            return
        }
        while (!exit) {
            try {
                try {
                    socket = serverSocket.accept()
                }catch (t:Throwable){
                    continue
                }
                Log.d(TAG, "client connected")
                socketout = socket.getOutputStream()
                dataOutput = ByteObjectOutputStream(socketout)
                while (!exit) {
                    val video: Writable? = bufferListVideo.peek ()
                    if (video != null) {
                        val tmp = buildVideoPack(video)
                        dataOutput.writeObject(tmp)
                        Log.d(TAG, "has send video pack")
                        bufferListVideo.remove(video)
                    }
                    val voice: Writable? = bufferListVoice.peek()
                    if (voice != null) {
                        val tmp = buildVoicePack(voice)
                        dataOutput.writeObject(tmp)
                        Log.d(TAG, "has send voice pack")
                        bufferListVoice.remove(voice)
                    }
                }
                dataOutput.close()
            } catch (e: Throwable) {
                e.printStackTrace()
                socket.close()
                Log.d(TAG, "client has disconnect")

            }
        }
        close()
    }

    fun putVoicePack(writable: Writable) {
        bufferListVoice.offer(writable)
    }

    fun putVideoPack(writable: Writable?) {
        bufferListVideo.offer(writable)
    }

    private fun close() {
        serverSocket.close()
        Log.d(TAG, "ιεΊε?ζ")
    }

    fun exit() {
        Log.d(TAG, "ιεΊδΈ­")
        exit = true
        interrupt()
    }

    abstract fun onError(t:Throwable)

}