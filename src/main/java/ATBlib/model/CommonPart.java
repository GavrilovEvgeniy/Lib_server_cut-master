package ATBlib.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class CommonPart {

    @Column(name = "Part Number")
    private String partNumber;

    @Column(name = "DEVICE")
    private String device;

    @Column(name = "Description")
    private String description;

    @Column(name = "Library Ref")
    private String libraryRef;

    @Column(name = "Library Path")
    private String libraryPath;

    @Column(name = "Footprint Ref")
    private String footprintRef;

    @Column(name = "Footprint Path")
    private String footprintPath;

    @Column(name = "Place")
    private String place;

    @Column(name = "Size")
    private String size;

    @Column(name = "Value")
    private String value;

    @Column(name = "ComponentLink1Description")
    private String componentLink1Description;

    @Column(name = "Manufacturer")
    private String manufacturer;

    @Column(name = "ComponentLink1URL")
    private String componentLink1URL;

    @Column(name = "Datasheet")
    private String datasheet;

    @Column(name = "Last changed")
    private String lastChanged;

    @Column(name = "Check")
    private String check;

    @Column(name = "Annotation")
    private String annotation;

    @Column(name = "Notused")
    private String notUsed;

}
