package org.hzero.generator.util;

public class XmlUtils {

    public static void main(String[] args) {
        org.jdom2.Document document=new org.jdom2.Document();
        org.jdom2.Element root=new org.jdom2.Element("rss");
        root.setAttribute("version", "2.0");
        root.addContent(
                new org.jdom2.Element("channel").addContent(
                        new org.jdom2.Element("title").setText("新闻国内")));
        document.setRootElement(root);
    }
    
}
