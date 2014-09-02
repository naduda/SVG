package jaxb.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class CP {
	@XmlAttribute(name="nameU", namespace="http://schemas.microsoft.com/visio/2003/SVGExtensions/")
	private String nameU;
	@XmlAttribute(name="lbl", namespace="http://schemas.microsoft.com/visio/2003/SVGExtensions/")
	private String lbl;
	@XmlAttribute(name="val", namespace="http://schemas.microsoft.com/visio/2003/SVGExtensions/")
	private String val;
	
	@Override
	public String toString() {
		return nameU;
	}

	public String getNameU() {
		return nameU;
	}

	public void setNameU(String nameU) {
		this.nameU = nameU;
	}

	public String getLbl() {
		return lbl;
	}

	public void setLbl(String lbl) {
		this.lbl = lbl;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
