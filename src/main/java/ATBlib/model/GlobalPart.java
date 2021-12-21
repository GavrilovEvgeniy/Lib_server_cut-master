package ATBlib.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class GlobalPart extends CommonPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Voltage")
    private String voltage;

    @Column(name = "Tolerance")
    private String tolerance;

    @Column(name = "Analog")
    private String analog;

    @Column(name = "Symbol")
    private String symbol;

    @Column(name = "PCB Footprint")
    private String pcbFootprint;

    @Column(name = "Param")
    private String param;

    @Column(name = "Temperature")
    private String temperature;

    @Column(name = "NameFromManufacturer")
    private String nameFromManufacturer;

    @Column(name = "Current")
    private String current;

    @Column(name = "DC Resistance")
    private String DC_resistance;

    @Column(name = "Annotation1")
    private String annotation1;

    public GlobalPart() {
    }

}