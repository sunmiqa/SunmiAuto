package sunmi.com.sunmiauto;

/**
 * Created by Administrator on 2017/6/27.
 */

import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SimpleMail {

    // 以文本格式发送邮件
    public static boolean sendTextMail(MailInfo mailInfo) {
        //判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties properties = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            //如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
        }

        //根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
        try {
            Message mailMessage = new MimeMessage(sendMailSession);//根据session创建一个邮件消息
            Address from = new InternetAddress(mailInfo.getFromAddress());//创建邮件发送者地址
            mailMessage.setFrom(from);//设置邮件消息的发送者
            Address to = new InternetAddress(mailInfo.getToAddress());//创建邮件的接收者地址
            mailMessage.setRecipient(Message.RecipientType.TO, to);//设置邮件消息的接收者
            mailMessage.setSubject(mailInfo.getSubject());//设置邮件消息的主题
            mailMessage.setSentDate(new Date());//设置邮件消息发送的时间
            //mailMessage.setText(mailInfo.getContent());//设置邮件消息的主要内容

            //MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含附件内容的MimeBodyPart
            //设置HTML内容
            messageBodyPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8");
            mainPart.addBodyPart(messageBodyPart);

            //存在附件
            String[] filePaths = mailInfo.getAttachFileNames();
            if (filePaths != null && filePaths.length > 0) {
                for(String filePath:filePaths){
                    messageBodyPart = new MimeBodyPart();
                    File file = new File(filePath);
                    if(file.exists()){//附件存在磁盘中
                        FileDataSource fds = new FileDataSource(file);//得到数据源
                        messageBodyPart.setDataHandler(new DataHandler(fds));//得到附件本身并至入BodyPart
                        messageBodyPart.setFileName(file.getName());//得到文件名同样至入BodyPart
                        mainPart.addBodyPart(messageBodyPart);
                    }
                }
            }

            //将MimeMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            Transport.send(mailMessage);//发送邮件
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 以HTML格式发送邮件
    public static boolean sendHtmlMail(MailInfo mailInfo) {
        Log.v("entersend","777777777777");
        //判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties properties = mailInfo.getProperties();
        if(properties.equals(null)){
            Log.v("entersend","333333333");
        }
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
        }

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
        if(sendMailSession.equals(null)){
            Log.v("entersend","44444444");
        }
        try{
            Message mailMessage = new MimeMessage(sendMailSession);//根据session创建一个邮件消息
            Address from = new InternetAddress(mailInfo.getFromAddress());//创建邮件发送者地址
            mailMessage.setFrom(from);//设置邮件消息的发送者
            Address to = new InternetAddress(mailInfo.getToAddress());//创建邮件的接收者地址
            mailMessage.setRecipient(Message.RecipientType.TO, to);//设置邮件消息的接收者
            mailMessage.setSubject(mailInfo.getSubject());//设置邮件消息的主题
            mailMessage.setSentDate(new Date());//设置邮件消息发送的时间

            //MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();//创建一个包含HTML内容的MimeBodyPart
            //设置HTML内容
            messageBodyPart.setContent(mailInfo.getContent(),"text/html; charset=utf-8");
            mainPart.addBodyPart(messageBodyPart);

            //存在附件
            String[] filePaths = mailInfo.getAttachFileNames();
            if(filePaths == null){
                Log.v("filepath","fffffff");
            }
            if (filePaths != null && filePaths.length > 0) {
                for(String filePath:filePaths){
                    messageBodyPart = new MimeBodyPart();
                    File file = new File(filePath);
                    if(file.exists()){//附件存在磁盘中
                        FileDataSource fds = new FileDataSource(file);//得到数据源
                        messageBodyPart.setDataHandler(new DataHandler(fds));//得到附件本身并至入BodyPart
                        messageBodyPart.setFileName(file.getName());//得到文件名同样至入BodyPart
                        mainPart.addBodyPart(messageBodyPart);
                    }
                }
            }

            //将MimeMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // add handlers for main MIME types
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            Transport.send(mailMessage);//发送邮件
            return true;
        }catch (Exception e) {
            Log.v("ssss","888888888");
            e.printStackTrace();

        }
        return false;
    }

}
