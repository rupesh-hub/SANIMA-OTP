package com.infodev.sanimaotp.services.utils;

import com.infodev.sanimaotp.exception.ChannelNotFoundException;

import java.util.LinkedHashSet;
import java.util.Set;

public class OTPChannel {
    private Set<String> channelSet;

    public OTPChannel() {
        Set<String> channelSet = new LinkedHashSet<>();
        channelSet.add("APP1_MB");
        channelSet.add("APP2_KIOSK");
        channelSet.add("APP3_ATM");
        channelSet.add("APP4_VBV");
        channelSet.add("APP5_IBANK");
        channelSet.add("APP6_VBV_CR");
        channelSet.add("APP7_KIOSKCR");
        channelSet.add("APP8_FT_SIG");
        this.channelSet = channelSet;
    }

    public int validateChannel(String channel) throws ChannelNotFoundException {
        if (this.channelSet.contains(channel)) {
            return 1;
        } else {
            throw new ChannelNotFoundException("Invalid token.");
        }
    }
}
