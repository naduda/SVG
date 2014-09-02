package jaxb.objects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustProps {
	@XmlElement(name="cp", namespace="http://schemas.microsoft.com/visio/2003/SVGExtensions/")
	private List<CP> customProps;

	public List<CP> getCustomProps() {
		return customProps;
	}

	public void setCustomProps(List<CP> customProps) {
		this.customProps = customProps;
	}
	
}
