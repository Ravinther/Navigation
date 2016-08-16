package com.sygic.aura.helper.imageMetadataExtractor.metadata.iptc;

import com.sygic.aura.helper.imageMetadataExtractor.metadata.Directory;
import java.util.HashMap;

public class IptcDirectory extends Directory {
    protected static final HashMap<Integer, String> _tagNameMap;

    static {
        _tagNameMap = new HashMap();
        _tagNameMap.put(Integer.valueOf(256), "Enveloped Record Version");
        _tagNameMap.put(Integer.valueOf(261), "Destination");
        _tagNameMap.put(Integer.valueOf(276), "File Format");
        _tagNameMap.put(Integer.valueOf(278), "File Version");
        _tagNameMap.put(Integer.valueOf(286), "Service Identifier");
        _tagNameMap.put(Integer.valueOf(296), "Envelope Number");
        _tagNameMap.put(Integer.valueOf(306), "Product Identifier");
        _tagNameMap.put(Integer.valueOf(316), "Envelope Priority");
        _tagNameMap.put(Integer.valueOf(326), "Date Sent");
        _tagNameMap.put(Integer.valueOf(336), "Time Sent");
        _tagNameMap.put(Integer.valueOf(346), "Coded Character Set");
        _tagNameMap.put(Integer.valueOf(356), "Unique Object Name");
        _tagNameMap.put(Integer.valueOf(376), "ARM Identifier");
        _tagNameMap.put(Integer.valueOf(378), "ARM Version");
        _tagNameMap.put(Integer.valueOf(512), "Application Record Version");
        _tagNameMap.put(Integer.valueOf(515), "Object Type Reference");
        _tagNameMap.put(Integer.valueOf(516), "Object Attribute Reference");
        _tagNameMap.put(Integer.valueOf(517), "Object Name");
        _tagNameMap.put(Integer.valueOf(519), "Edit Status");
        _tagNameMap.put(Integer.valueOf(520), "Editorial Update");
        _tagNameMap.put(Integer.valueOf(522), "Urgency");
        _tagNameMap.put(Integer.valueOf(524), "Subject Reference");
        _tagNameMap.put(Integer.valueOf(527), "Category");
        _tagNameMap.put(Integer.valueOf(532), "Supplemental Category(s)");
        _tagNameMap.put(Integer.valueOf(534), "Fixture Identifier");
        _tagNameMap.put(Integer.valueOf(537), "Keywords");
        _tagNameMap.put(Integer.valueOf(538), "Content Location Code");
        _tagNameMap.put(Integer.valueOf(539), "Content Location Name");
        _tagNameMap.put(Integer.valueOf(542), "Release Date");
        _tagNameMap.put(Integer.valueOf(547), "Release Time");
        _tagNameMap.put(Integer.valueOf(549), "Expiration Date");
        _tagNameMap.put(Integer.valueOf(550), "Expiration Time");
        _tagNameMap.put(Integer.valueOf(552), "Special Instructions");
        _tagNameMap.put(Integer.valueOf(554), "Action Advised");
        _tagNameMap.put(Integer.valueOf(557), "Reference Service");
        _tagNameMap.put(Integer.valueOf(559), "Reference Date");
        _tagNameMap.put(Integer.valueOf(562), "Reference Number");
        _tagNameMap.put(Integer.valueOf(567), "Date Created");
        _tagNameMap.put(Integer.valueOf(572), "Time Created");
        _tagNameMap.put(Integer.valueOf(574), "Digital Date Created");
        _tagNameMap.put(Integer.valueOf(575), "Digital Time Created");
        _tagNameMap.put(Integer.valueOf(577), "Originating Program");
        _tagNameMap.put(Integer.valueOf(582), "Program Version");
        _tagNameMap.put(Integer.valueOf(587), "Object Cycle");
        _tagNameMap.put(Integer.valueOf(592), "By-line");
        _tagNameMap.put(Integer.valueOf(597), "By-line Title");
        _tagNameMap.put(Integer.valueOf(602), "City");
        _tagNameMap.put(Integer.valueOf(604), "Sub-location");
        _tagNameMap.put(Integer.valueOf(607), "Province/State");
        _tagNameMap.put(Integer.valueOf(612), "Country/Primary Location Code");
        _tagNameMap.put(Integer.valueOf(613), "Country/Primary Location Name");
        _tagNameMap.put(Integer.valueOf(615), "Original Transmission Reference");
        _tagNameMap.put(Integer.valueOf(617), "Headline");
        _tagNameMap.put(Integer.valueOf(622), "Credit");
        _tagNameMap.put(Integer.valueOf(627), "Source");
        _tagNameMap.put(Integer.valueOf(628), "Copyright Notice");
        _tagNameMap.put(Integer.valueOf(630), "Contact");
        _tagNameMap.put(Integer.valueOf(632), "Caption/Abstract");
        _tagNameMap.put(Integer.valueOf(633), "Local Caption");
        _tagNameMap.put(Integer.valueOf(634), "Caption Writer/Editor");
        _tagNameMap.put(Integer.valueOf(637), "Rasterized Caption");
        _tagNameMap.put(Integer.valueOf(642), "Image Type");
        _tagNameMap.put(Integer.valueOf(643), "Image Orientation");
        _tagNameMap.put(Integer.valueOf(647), "Language Identifier");
        _tagNameMap.put(Integer.valueOf(662), "Audio Type");
        _tagNameMap.put(Integer.valueOf(663), "Audio Sampling Rate");
        _tagNameMap.put(Integer.valueOf(664), "Audio Sampling Resolution");
        _tagNameMap.put(Integer.valueOf(665), "Audio Duration");
        _tagNameMap.put(Integer.valueOf(666), "Audio Outcue");
        _tagNameMap.put(Integer.valueOf(696), "Job Identifier");
        _tagNameMap.put(Integer.valueOf(697), "Master Document Identifier");
        _tagNameMap.put(Integer.valueOf(698), "Short Document Identifier");
        _tagNameMap.put(Integer.valueOf(699), "Unique Document Identifier");
        _tagNameMap.put(Integer.valueOf(700), "Owner Identifier");
        _tagNameMap.put(Integer.valueOf(712), "Object Data Preview File Format");
        _tagNameMap.put(Integer.valueOf(713), "Object Data Preview File Format Version");
        _tagNameMap.put(Integer.valueOf(714), "Object Data Preview Data");
    }

    public IptcDirectory() {
        setDescriptor(new IptcDescriptor(this));
    }

    public String getName() {
        return "IPTC";
    }

    protected HashMap<Integer, String> getTagNameMap() {
        return _tagNameMap;
    }
}
