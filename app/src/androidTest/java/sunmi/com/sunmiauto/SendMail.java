package sunmi.com.sunmiauto;

import android.util.Log;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public void sendEmail(String sendContent) throws MessagingException {
		Log.i("mmmm","uuuu");
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.exmail.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		Calendar date = Calendar.getInstance();
		msg.setSubject("Settings自动化测试结果_" + date.getTime());
		// 设置邮件内容
		msg.setText(sendContent);
		// 设置发件人
		msg.setFrom(new InternetAddress("zhangjiyang@sunmi.com"));

		Transport transport = session.getTransport();
		// 连接邮件服务器
		transport.connect("zhangjiyang@sunmi.com", "Sunmi2017");
		// 发送邮件
		transport.sendMessage(msg, new Address[] { new InternetAddress("zhangjiyang@sunmi.com") });
		// 关闭连接
		transport.close();
	}
}