package com.example.sip;

import android.gov.nist.javax.sdp.MediaDescriptionImpl;
import android.gov.nist.javax.sdp.SessionDescriptionImpl;
import android.gov.nist.javax.sdp.TimeDescriptionImpl;
import android.gov.nist.javax.sdp.fields.AttributeField;
import android.gov.nist.javax.sdp.fields.ConnectionField;
import android.gov.nist.javax.sdp.fields.MediaField;
import android.gov.nist.javax.sdp.fields.OriginField;
import android.gov.nist.javax.sdp.fields.ProtoVersionField;
import android.gov.nist.javax.sdp.fields.SessionNameField;
import android.gov.nist.javax.sdp.fields.TimeField;
import android.gov.nist.javax.sip.header.Contact;
import android.gov.nist.javax.sip.header.ContentLength;
import android.javax.sdp.SdpException;
import android.javax.sdp.SdpFactory;
import android.javax.sdp.SessionDescription;
import android.javax.sip.ClientTransaction;
import android.javax.sip.Dialog;
import android.javax.sip.DialogTerminatedEvent;
import android.javax.sip.IOExceptionEvent;
import android.javax.sip.InvalidArgumentException;
import android.javax.sip.ListeningPoint;
import android.javax.sip.PeerUnavailableException;
import android.javax.sip.RequestEvent;
import android.javax.sip.ResponseEvent;
import android.javax.sip.ServerTransaction;
import android.javax.sip.SipException;
import android.javax.sip.SipFactory;
import android.javax.sip.SipListener;
import android.javax.sip.SipProvider;
import android.javax.sip.SipStack;
import android.javax.sip.TimeoutEvent;
import android.javax.sip.TransactionTerminatedEvent;
import android.javax.sip.address.Address;
import android.javax.sip.address.AddressFactory;
import android.javax.sip.address.SipURI;
import android.javax.sip.address.URI;
import android.javax.sip.header.CSeqHeader;
import android.javax.sip.header.CallIdHeader;
import android.javax.sip.header.ContactHeader;
import android.javax.sip.header.ContentTypeHeader;
import android.javax.sip.header.FromHeader;
import android.javax.sip.header.HeaderFactory;
import android.javax.sip.header.MaxForwardsHeader;
import android.javax.sip.header.ToHeader;
import android.javax.sip.header.ViaHeader;
import android.javax.sip.message.MessageFactory;
import android.javax.sip.message.Request;
import android.javax.sip.message.Response;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import cn.hutool.core.util.IdUtil;

public class SipStackAndroid implements SipListener {
    private static SipStackAndroid instance = null;
    public static SipStack sipStack;
    public static SipProvider sipProvider;
    public static HeaderFactory headerFactory;
    public static AddressFactory addressFactory;
    public static MessageFactory messageFactory;
    public static SipFactory sipFactory;

    public static ListeningPoint udpListeningPoint;

    public static String localIp;
    public static int localPort = 15060;
    public static String localEndpoint = localIp + ":" + localPort;
    public static String transport = "udp";

    public static String remoteIp = "192.168.107.33";
    public static int remotePort = 50004;
    public static String remoteEndpoint = remoteIp + ":" + remotePort;
    // Save the created ACK request, to respond to retransmitted 2xx
    private Request ackRequest;
    private static Dialog dialog;
    public static String meetingId;//会议id
    public static String rtmpUrl="";//会议rtmp地址

    protected SipStackAndroid() {
        initialize();
    }

    public static SipStackAndroid getInstance() {
        if (instance == null) {
            instance = new SipStackAndroid();
        }
        return instance;
    }

    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress instanceof Inet4Address) {//换成Inet6Address 就可以拿到ipv6的地址
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void initialize() {
        localIp = getLocalIpAddress();
        localEndpoint = localIp + ":" + localPort;
        remoteEndpoint = remoteIp + ":" + remotePort;
        sipStack = null;
        sipFactory = SipFactory.getInstance();
        sipFactory.setPathName("android.gov.nist");
        Properties properties = new Properties();
//        properties.setProperty("javaxx.sip.OUTBOUND_PROXY", remoteEndpoint + "/"
//                + transport);
        properties.setProperty("android.javax.sip.STACK_NAME", "androidSip");
        try {
            // Create SipStack object
            sipStack = sipFactory.createSipStack(properties);
            System.out.println("createSipStack " + sipStack);
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        try {
            headerFactory = sipFactory.createHeaderFactory();
            addressFactory = sipFactory.createAddressFactory();
            messageFactory = sipFactory.createMessageFactory();
            udpListeningPoint = sipStack.createListeningPoint(localIp,
                    localPort, transport);
            sipProvider = sipStack.createSipProvider(udpListeningPoint);
            sipProvider.addSipListener(this);
//            if (instance == null){
//                sipProvider.addSipListener(SipStackAndroid.getInstance());
//            }else{
//                sipProvider.addSipListener(instance);
//            }

            // this.send_register();
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Creating Listener Points");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        ServerTransaction serverTransactionId = requestEvent
                .getServerTransaction();

        System.out.println("\n\nRequest " + request.getMethod()
                + " received at " + sipStack.getStackName()
                + " with server transaction id " + serverTransactionId);

        // We are the UAC so the only request we get is the BYE.
        if (request.getMethod().equals(Request.BYE)) {
            processBye(request, serverTransactionId);
        }else {
            try {
                serverTransactionId.sendResponse( messageFactory.createResponse(200,request) );
            } catch (SipException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(request.getMethod().equals(Request.INFO)){
                ContentLength contentLen = (ContentLength)request.getContentLength();
                if (contentLen != null && contentLen.getContentLength() != 0) {
                    byte[] contentByte = (byte[]) request.getContent();
                    String content=new String(contentByte);
                    if(content.indexOf("EventType=\"Heartbeat\"")>-1){
                        StringBuffer sb = new StringBuffer();
                        sb.append(content).insert(content.indexOf("</SIP_XML>"),"<result response=\"200\" />");
                        String heartContent=sb.toString();
                        try {
                            this.sendRequestHeartbeat(heartContent, (ContentTypeHeader) request.getHeader(ContentTypeHeader.NAME));
                        } catch (SipException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void processBye(Request request,
                           ServerTransaction serverTransactionId) {
        try {
            System.out.println("shootist:  got a bye .");
            if (serverTransactionId == null) {
                System.out.println("shootist:  null TID.");
                return;
            }
            Dialog dialog = serverTransactionId.getDialog();
            System.out.println("Dialog State = " + dialog.getState());
            Response response = messageFactory.createResponse(200, request);
            serverTransactionId.sendResponse(response);
            System.out.println("shootist:  Sending OK.");
            System.out.println("Dialog State = " + dialog.getState());

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void sendRequestBye() throws SipException {
        Request byeRequest = this.dialog.createRequest(Request.BYE);
        ClientTransaction ct = sipProvider.getNewClientTransaction(byeRequest);
        dialog.sendRequest(ct);
    }

    public void sendRequestHeartbeat(String heartContent, ContentTypeHeader header) throws SipException, ParseException {
        Request heartbeatRequest = this.dialog.createRequest(Request.INFO);
        heartbeatRequest.setContent(heartContent,header);
        ClientTransaction ct = sipProvider.getNewClientTransaction(heartbeatRequest);
        dialog.sendRequest(ct);
    }

    @Override
    public void processResponse(ResponseEvent responseReceivedEvent) {
        System.out.println("Got a response");
        Response response = (Response) responseReceivedEvent.getResponse();
        ClientTransaction tid = responseReceivedEvent.getClientTransaction();
        CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
        String reasonPhrase= response.getReasonPhrase();
        System.out.println("Response received : Status Code = "
                + response.getStatusCode() + " " + cseq);

        try {
            if (response.getStatusCode() == Response.OK) {
                if (cseq.getMethod().equals(Request.INVITE)&&reasonPhrase.equals("OK")) {
                    ackRequest = dialog.createAck( ((CSeqHeader) response.getHeader(CSeqHeader.NAME)).getSeqNumber() );
                    System.out.println("Sending ACK");
                    dialog.sendAck(ackRequest);
                    Contact contact= (Contact) response.getHeader(ContactHeader.NAME);
                    String contactValue=contact.getValue();
                    meetingId=contactValue.substring(contactValue.indexOf("sip:conference_")+15,contactValue.indexOf("@"));
                    // JvB: test REFER, reported bug in tag handling
                    // dialog.sendRequest(  sipProvider.getNewClientTransaction( dialog.createRequest("REFER") ));
                    //获取消息内容
                    ContentLength contentLen = (ContentLength)response.getContentLength();
                    if (contentLen != null && contentLen.getContentLength() != 0)
                    {
                        byte[] sessionDescriptionByte= (byte[]) response.getContent();
                        SessionDescription sessionDescription=SdpFactory.getInstance().createSessionDescription(new String(sessionDescriptionByte));
                        Vector<MediaDescriptionImpl> mediaDescriptions=sessionDescription.getMediaDescriptions(false);
                        for(MediaDescriptionImpl mediaDescription:mediaDescriptions){
                            Vector<AttributeField> attributeFields =mediaDescription.getAttributeFields();
                            for(AttributeField attributeField:attributeFields){
                                if("sendonly".equals(attributeField.getName())){
                                    String ip=mediaDescription.getConnectionField().getAddress();
                                    int port=mediaDescription.getMediaField().getPort();
                                    String protocol=mediaDescription.getAttribute("fmtp");
                                    String playPath="";
                                    String[] protocalValues=protocol.split(";");
                                    for(String pvalue:protocalValues){
                                        if(pvalue.startsWith("playpath=")){
                                            playPath=pvalue.substring(pvalue.indexOf("playpath=")+9);
                                            rtmpUrl="rtmp://"+ip+":"+port+"/"+playPath;

                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {

    }

    @Override
    public void processIOException(IOExceptionEvent ioExceptionEvent) {

    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {

    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {

    }

    public void createMeeting() throws SdpException, ParseException, SipException, InvalidArgumentException {
        SessionDescription sessionDescription=createSdp();
        register("sip:conference_factory@"+remoteIp+":"+remotePort,sessionDescription);
    }

    public void joinMetting(String meetingId) throws InvalidArgumentException, SipException, SdpException, ParseException {
        SessionDescription sessionDescription=createSdp();
        register("sip:conference_"+meetingId+"@"+remoteIp+":"+remotePort,sessionDescription);
    }

    private SessionDescription createSdp() throws SdpException, ParseException, SipException, InvalidArgumentException{
        SessionDescriptionImpl sessionDescription=new SessionDescriptionImpl();
        ProtoVersionField version=new ProtoVersionField();
        version.setProtoVersion(0);
        sessionDescription.setVersion(version);
        OriginField origin=new OriginField();
        origin.setUsername("test1");
        origin.setSessionId(63045986);
        origin.setSessionVersion(82947337);
        origin.setNetworkType("IN");
        origin.setAddressType("IP4");
        origin.setAddress(localIp);
        sessionDescription.setOrigin(origin);
        SessionNameField sessionNameField=new SessionNameField();
        sessionNameField.setSessionName("sip call");
        sessionDescription.setSessionName(sessionNameField);
        ConnectionField connectionField=new ConnectionField();
        connectionField.setNetworkType("IN");
        connectionField.setAddressType("IP4");
        connectionField.setAddress(localIp);
        sessionDescription.setConnection(connectionField);
        TimeField timeField=new TimeField();
        timeField.setStartTime(0);
        timeField.setStopTime(0);
        TimeDescriptionImpl timeDescription=new TimeDescriptionImpl(timeField);
        Vector timeDescriptions=new Vector();
        timeDescriptions.add(timeDescription);
        sessionDescription.setTimeDescriptions(timeDescriptions);
        //媒体信息
        Vector mediaDescriptions=new Vector();

        Vector attributes=new Vector();
        AttributeField attributeField=new AttributeField();
        attributeField.setValue("recvonly");
        attributes.add(attributeField);
        attributeField=new AttributeField();
        attributeField.setName("fmtp");
        attributeField.setValue("96 protocol=rtmp;playpath=live/mixed96;audio=disabled;");
        attributes.add(attributeField);
        Vector formats=new Vector();
        formats.add(96);
        MediaDescriptionImpl mediaDescription=createMediaInfo(localIp,1935,attributes,formats);//创建会议接受信息可以随便写
        mediaDescriptions.add(mediaDescription);
        //发送媒体信息
        Vector sendAttributes=new Vector();
        AttributeField sendAttributeField=new AttributeField();
        sendAttributeField.setValue("sendonly");
        sendAttributes.add(sendAttributeField);
        sendAttributeField=new AttributeField();
        sendAttributeField.setName("content");
        sendAttributeField.setValue("main");
        sendAttributes.add(sendAttributeField);
        sendAttributeField=new AttributeField();
        sendAttributeField.setName("fmtp");
        sendAttributeField.setValue("102 type=mainstream;protocol=rtmp;playpath=live/sub;audio=disabled;");
        sendAttributes.add(sendAttributeField);
        Vector sendFormats=new Vector();
        sendFormats.add(102);
        MediaDescriptionImpl sendMediaDescription=createMediaInfo("192.168.107.86",1935,sendAttributes,sendFormats);
        mediaDescriptions.add(sendMediaDescription);

        Vector vgaSendAttributes=new Vector();
        AttributeField vgaAttributeField=new AttributeField();
        vgaAttributeField.setValue("sendonly");
        vgaSendAttributes.add(vgaAttributeField);
        vgaAttributeField=new AttributeField();
        vgaAttributeField.setName("content");
        vgaAttributeField.setValue("vga");
        vgaSendAttributes.add(vgaAttributeField);
        vgaAttributeField=new AttributeField();
        vgaAttributeField.setName("fmtp");
        vgaAttributeField.setValue("104 type=mainstream;protocol=rtmp;playpath=live/screen;audio=disabled;");
        vgaSendAttributes.add(vgaAttributeField);
        Vector vgaFormats=new Vector();
        vgaFormats.add(104);
        MediaDescriptionImpl vgaSendMediaDescription=createMediaInfo("192.168.107.86",1935,vgaSendAttributes,vgaFormats);
        mediaDescriptions.add(vgaSendMediaDescription);

        sessionDescription.setMediaDescriptions(mediaDescriptions);
        return sessionDescription;
    }

    private MediaDescriptionImpl createMediaInfo(String ip, int port, Vector attributes, Vector formats) throws SdpException {
        MediaDescriptionImpl mediaDescription=new MediaDescriptionImpl();
        MediaField mediaField=new MediaField();
        mediaField.setMedia("application");
        mediaField.setPort(port);
        mediaField.setProto("TCP");
        mediaField.setFormats(formats);
        mediaDescription.setMedia(mediaField);
        ConnectionField connectionField=new ConnectionField();
        connectionField.setNetworkType("IN");
        connectionField.setAddressType("IP4");
        connectionField.setAddress(ip);
        mediaDescription.setConnectionField(connectionField);
        mediaDescription.setAttributes(attributes);
        return mediaDescription;
    }

    private void register(String to, Object message) throws ParseException,
            InvalidArgumentException, SipException {
        SipURI from = addressFactory.createSipURI("test1", localEndpoint);

        Address fromNameAddress = addressFactory.createAddress(from);

        // fromNameAddress.setDisplayName(sipUsername);
        FromHeader fromHeader = headerFactory.createFromHeader(fromNameAddress,
                IdUtil.fastSimpleUUID());

        URI toAddress = addressFactory.createURI(to);

        Address toNameAddress = addressFactory.createAddress(toAddress);

        // toNameAddress.setDisplayName(username);
        ToHeader toHeader = headerFactory.createToHeader(toNameAddress, null);

        URI requestURI = addressFactory.createURI(to);
        // requestURI.setTransportParam("udp");

        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = headerFactory.createViaHeader(SipStackAndroid.localIp, SipStackAndroid.localPort,
                SipStackAndroid.transport, null);
        // add via headers
        viaHeaders.add(viaHeader);

        CallIdHeader callIdHeader = sipProvider.getNewCallId();

        CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1,
                Request.INVITE);

        MaxForwardsHeader maxForwards = headerFactory
                .createMaxForwardsHeader(70);

        Request request = messageFactory.createRequest(requestURI,
                Request.INVITE, callIdHeader, cSeqHeader, fromHeader,
                toHeader, viaHeaders, maxForwards);

//        SupportedHeader supportedHeader = SipStackAndroid.headerFactory
//                .createSupportedHeader("replaces, outbound");
//        request.addHeader(supportedHeader);

        ContactHeader contactHeader = headerFactory.createContactHeader(fromNameAddress);
        request.addHeader(contactHeader);

//        SipURI routeUri = SipStackAndroid.addressFactory.createSipURI(null, instance.remoteIp);
//        routeUri.setTransportParam(SipStackAndroid.transport);
//        routeUri.setLrParam();
//        routeUri.setPort(SipStackAndroid.remotePort);
//
//        Address routeAddress = SipStackAndroid.addressFactory.createAddress(routeUri);
//        RouteHeader route =SipStackAndroid.headerFactory.createRouteHeader(routeAddress);
//        request.addHeader(route);
        ContentTypeHeader contentTypeHeader = headerFactory
                .createContentTypeHeader("application", "sdp");
        request.setContent(message, contentTypeHeader);
        System.out.println(request);
        ClientTransaction transaction = sipProvider
                .getNewClientTransaction(request);
        // Send the request statefully, through the client transaction.
        transaction.sendRequest();
        dialog = transaction.getDialog();
    }
}
