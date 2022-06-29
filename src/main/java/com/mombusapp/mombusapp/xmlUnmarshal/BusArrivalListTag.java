package com.mombusapp.mombusapp.xmlUnmarshal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@ToString
public class BusArrivalListTag {
    @XmlElement(name="predictTime1")
    private String predictTime1;
    @XmlElement(name="predictTime2")
    private String predictTime2;
    @XmlElement(name="routeId")
    private String routeId;
}
