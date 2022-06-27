package com.gamenet.cruscottofatturazione.context;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RFCException", propOrder = {
    "msg",
    "msgCode"
})
public class RFCException {

    @XmlElementRef(name = "msg", type = JAXBElement.class, required = false)
    protected JAXBElement<String> msg;
    @XmlElementRef(name = "msgCode", type = JAXBElement.class, required = false)
    protected JAXBElement<String> msgCode;

    /**
     * Gets the value of the msg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMsg() {
        return msg;
    }

    /**
     * Sets the value of the msg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMsg(JAXBElement<String> value) {
        this.msg = value;
    }

    /**
     * Gets the value of the msgCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMsgCode() {
        return msgCode;
    }

    /**
     * Sets the value of the msgCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMsgCode(JAXBElement<String> value) {
        this.msgCode = value;
    }

}
