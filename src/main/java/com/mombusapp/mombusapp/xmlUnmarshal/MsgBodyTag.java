package com.mombusapp.mombusapp.xmlUnmarshal;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class MsgBodyTag {
    @XmlElement(name="busArrivalList")
    private List<BusArrivalListTag> busArrivalListTags;
}
