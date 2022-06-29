package com.mombusapp.mombusapp.xmlUnmarshal;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="response")
@Getter @Setter @ToString
public class XmlResponseTag {
    @XmlElement(name="msgBody")
    private MsgBodyTag[] msgBodyTags;

}
