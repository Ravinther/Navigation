package com.sygic.aura.helper.imageMetadataExtractor.metadata.exif;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class ExifSubIFDDirectory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(266), "Fill Order");
        _tagNameMap.put(Integer.valueOf(269), "Document Name");
        _tagNameMap.put(Integer.valueOf(4096), "Related Image File Format");
        _tagNameMap.put(Integer.valueOf(4097), "Related Image Width");
        _tagNameMap.put(Integer.valueOf(4098), "Related Image Length");
        _tagNameMap.put(Integer.valueOf(342), "Transfer Range");
        _tagNameMap.put(Integer.valueOf(512), "JPEG Proc");
        _tagNameMap.put(Integer.valueOf(37122), "Compressed Bits Per Pixel");
        _tagNameMap.put(Integer.valueOf(37500), "Makernote");
        _tagNameMap.put(Integer.valueOf(40965), "Interoperability Offset");
        _tagNameMap.put(Integer.valueOf(254), "New Subfile Type");
        _tagNameMap.put(Integer.valueOf(255), "Subfile Type");
        _tagNameMap.put(Integer.valueOf(258), "Bits Per Sample");
        _tagNameMap.put(Integer.valueOf(262), "Photometric Interpretation");
        _tagNameMap.put(Integer.valueOf(263), "Thresholding");
        _tagNameMap.put(Integer.valueOf(273), "Strip Offsets");
        _tagNameMap.put(Integer.valueOf(277), "Samples Per Pixel");
        _tagNameMap.put(Integer.valueOf(278), "Rows Per Strip");
        _tagNameMap.put(Integer.valueOf(279), "Strip Byte Counts");
        _tagNameMap.put(Integer.valueOf(285), "Page Name");
        _tagNameMap.put(Integer.valueOf(284), "Planar Configuration");
        _tagNameMap.put(Integer.valueOf(301), "Transfer Function");
        _tagNameMap.put(Integer.valueOf(317), "Predictor");
        _tagNameMap.put(Integer.valueOf(322), "Tile Width");
        _tagNameMap.put(Integer.valueOf(323), "Tile Length");
        _tagNameMap.put(Integer.valueOf(324), "Tile Offsets");
        _tagNameMap.put(Integer.valueOf(325), "Tile Byte Counts");
        _tagNameMap.put(Integer.valueOf(347), "JPEG Tables");
        _tagNameMap.put(Integer.valueOf(530), "YCbCr Sub-Sampling");
        _tagNameMap.put(Integer.valueOf(33421), "CFA Repeat Pattern Dim");
        _tagNameMap.put(Integer.valueOf(33422), "CFA Pattern");
        _tagNameMap.put(Integer.valueOf(33423), "Battery Level");
        _tagNameMap.put(Integer.valueOf(33434), "Exposure Time");
        _tagNameMap.put(Integer.valueOf(33437), "F-Number");
        _tagNameMap.put(Integer.valueOf(33723), "IPTC/NAA");
        _tagNameMap.put(Integer.valueOf(34675), "Inter Color Profile");
        _tagNameMap.put(Integer.valueOf(34850), "Exposure Program");
        _tagNameMap.put(Integer.valueOf(34852), "Spectral Sensitivity");
        _tagNameMap.put(Integer.valueOf(34855), "ISO Speed Ratings");
        _tagNameMap.put(Integer.valueOf(34856), "Opto-electric Conversion Function (OECF)");
        _tagNameMap.put(Integer.valueOf(34857), "Interlace");
        _tagNameMap.put(Integer.valueOf(34858), "Time Zone Offset");
        _tagNameMap.put(Integer.valueOf(34859), "Self Timer Mode");
        _tagNameMap.put(Integer.valueOf(36864), "Exif Version");
        _tagNameMap.put(Integer.valueOf(36867), "Date/Time Original");
        _tagNameMap.put(Integer.valueOf(36868), "Date/Time Digitized");
        _tagNameMap.put(Integer.valueOf(37121), "Components Configuration");
        _tagNameMap.put(Integer.valueOf(37377), "Shutter Speed Value");
        _tagNameMap.put(Integer.valueOf(37378), "Aperture Value");
        _tagNameMap.put(Integer.valueOf(37379), "Brightness Value");
        _tagNameMap.put(Integer.valueOf(37380), "Exposure Bias Value");
        _tagNameMap.put(Integer.valueOf(37381), "Max Aperture Value");
        _tagNameMap.put(Integer.valueOf(37382), "Subject Distance");
        _tagNameMap.put(Integer.valueOf(37383), "Metering Mode");
        _tagNameMap.put(Integer.valueOf(37384), "Light Source");
        _tagNameMap.put(Integer.valueOf(37384), "White Balance");
        _tagNameMap.put(Integer.valueOf(37385), "Flash");
        _tagNameMap.put(Integer.valueOf(37386), "Focal Length");
        _tagNameMap.put(Integer.valueOf(37387), "Flash Energy");
        _tagNameMap.put(Integer.valueOf(37388), "Spatial Frequency Response");
        _tagNameMap.put(Integer.valueOf(37389), "Noise");
        _tagNameMap.put(Integer.valueOf(37393), "Image Number");
        _tagNameMap.put(Integer.valueOf(37394), "Security Classification");
        _tagNameMap.put(Integer.valueOf(37395), "Image History");
        _tagNameMap.put(Integer.valueOf(37396), "Subject Location");
        _tagNameMap.put(Integer.valueOf(41493), "Exposure Index");
        _tagNameMap.put(Integer.valueOf(37398), "TIFF/EP Standard ID");
        _tagNameMap.put(Integer.valueOf(37510), "User Comment");
        _tagNameMap.put(Integer.valueOf(37520), "Sub-Sec Time");
        _tagNameMap.put(Integer.valueOf(37521), "Sub-Sec Time Original");
        _tagNameMap.put(Integer.valueOf(37522), "Sub-Sec Time Digitized");
        _tagNameMap.put(Integer.valueOf(40960), "FlashPix Version");
        _tagNameMap.put(Integer.valueOf(40961), "Color Space");
        _tagNameMap.put(Integer.valueOf(40962), "Exif Image Width");
        _tagNameMap.put(Integer.valueOf(40963), "Exif Image Height");
        _tagNameMap.put(Integer.valueOf(40964), "Related Sound File");
        _tagNameMap.put(Integer.valueOf(41483), "Flash Energy");
        _tagNameMap.put(Integer.valueOf(41484), "Spatial Frequency Response");
        _tagNameMap.put(Integer.valueOf(41486), "Focal Plane X Resolution");
        _tagNameMap.put(Integer.valueOf(41487), "Focal Plane Y Resolution");
        _tagNameMap.put(Integer.valueOf(41488), "Focal Plane Resolution Unit");
        _tagNameMap.put(Integer.valueOf(41492), "Subject Location");
        _tagNameMap.put(Integer.valueOf(37397), "Exposure Index");
        _tagNameMap.put(Integer.valueOf(41495), "Sensing Method");
        _tagNameMap.put(Integer.valueOf(41728), "File Source");
        _tagNameMap.put(Integer.valueOf(41729), "Scene Type");
        _tagNameMap.put(Integer.valueOf(41730), "CFA Pattern");
        _tagNameMap.put(Integer.valueOf(41985), "Custom Rendered");
        _tagNameMap.put(Integer.valueOf(41986), "Exposure Mode");
        _tagNameMap.put(Integer.valueOf(41987), "White Balance Mode");
        _tagNameMap.put(Integer.valueOf(41988), "Digital Zoom Ratio");
        _tagNameMap.put(Integer.valueOf(41989), "Focal Length 35");
        _tagNameMap.put(Integer.valueOf(41990), "Scene Capture Type");
        _tagNameMap.put(Integer.valueOf(41991), "Gain Control");
        _tagNameMap.put(Integer.valueOf(41992), "Contrast");
        _tagNameMap.put(Integer.valueOf(41993), "Saturation");
        _tagNameMap.put(Integer.valueOf(41994), "Sharpness");
        _tagNameMap.put(Integer.valueOf(41995), "Device Setting Description");
        _tagNameMap.put(Integer.valueOf(41996), "Subject Distance Range");
        _tagNameMap.put(Integer.valueOf(42016), "Unique Image ID");
        _tagNameMap.put(Integer.valueOf(42032), "Camera Owner Name");
        _tagNameMap.put(Integer.valueOf(42033), "Body Serial Number");
        _tagNameMap.put(Integer.valueOf(42034), "Lens Specification");
        _tagNameMap.put(Integer.valueOf(42035), "Lens Make");
        _tagNameMap.put(Integer.valueOf(42036), "Lens Model");
        _tagNameMap.put(Integer.valueOf(42037), "Lens Serial Number");
        _tagNameMap.put(Integer.valueOf(42240), "Gamma");
        _tagNameMap.put(Integer.valueOf(280), "Minimum sample value");
        _tagNameMap.put(Integer.valueOf(281), "Maximum sample value");
        _tagNameMap.put(Integer.valueOf(65002), "Lens");
    }

    public ExifSubIFDDirectory() {
        setDescriptor(new ExifSubIFDDescriptor(this));
    }

    public String getName() {
        return "Exif SubIFD";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
