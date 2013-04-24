package com.dne.sie.common.exception;

/*
* <blockquote>
* <pre>
* 这是消息类的标志接口
* 实现这个接口的消息类只需要实现 <code>getText(String key)</code>方法
* 这个接口没有规定存储消息内容的方式
* 可以继承ListResourceBundle，例如 CESDataMessage
* 也可以使用属性配置，例如 PropertiesMessage
* </pre>
* </blockquote>
*/

public interface ComMessage {

    /**
     * 得到消息的内容
     * @return 文本
     */
    public String getText();
}

