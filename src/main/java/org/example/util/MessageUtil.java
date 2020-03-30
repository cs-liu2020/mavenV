package org.example.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.message.OutMsgEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    //定义一个私有的静态全局变量来保存该类的唯一实例

    private static MessageUtil messageUtil;

    /// 构造函数必须是私有的
    /// 这样在外部便无法使用 new 来创建该类的实例
    private MessageUtil() {
    }

    /// 则在类的外部便无需实例化就可以调用该方法
    public static MessageUtil getInstance() {
        if (messageUtil == null) {
            messageUtil = new MessageUtil();
        }
        return messageUtil;
    }

    //xml-map
    public Map<String, String> parseXml(HttpServletRequest request) throws Exception {

        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        String requestXml = document.asXML();
        String subXml = requestXml.split(">")[0] + ">";
        requestXml = requestXml.substring(subXml.length());
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的全部子节点
        List<Element> elementList = root.elements();
        // 遍历全部子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        map.put("requestXml", requestXml);
        // 释放资源
        inputStream.close();
        return map;

    }

    //请求转成xml
    public String parseMsgXml(HttpServletRequest request) {

        String responseMsg = null;
        try {
            InputStream is = request.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            responseMsg = new String(jsonBytes, "UTF-8");
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responseMsg;

    }

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */

    public String textMessageToXml(OutMsgEntity textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }


    /**
     * 扩展xstream，使其支持CDATA块
     */

    private XStream xstream = new XStream(new XppDriver() {

        public HierarchicalStreamWriter createWriter(Writer out) {

            return new PrettyPrintWriter(out) {
                // 对全部xml节点的转换都添加CDATA标记
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                    if (name.equals("CreateTime")) {
                        cdata = false;
                    }
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }

            };
        }

    });
}