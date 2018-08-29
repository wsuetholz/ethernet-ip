package com.digitalpetri.enip.cip.services;

import com.digitalpetri.enip.cip.CipResponseException;
import com.digitalpetri.enip.cip.epath.EPath.PaddedEPath;
import com.digitalpetri.enip.cip.structs.MessageRouterRequest;
import com.digitalpetri.enip.cip.structs.MessageRouterResponse;
import io.netty.buffer.ByteBuf;

public class GetAttributesAllService implements CipService<ByteBuf> {

    public static final int SERVICE_CODE = 0x01;

    private final PaddedEPath requestPath;

    public GetAttributesAllService(PaddedEPath requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public void encodeRequest(ByteBuf buffer) {
        MessageRouterRequest request = new MessageRouterRequest(
            SERVICE_CODE,
            requestPath,
            byteBuf -> {
            }
        );

        MessageRouterRequest.encode(request, buffer);
    }

    @Override
    public ByteBuf decodeResponse(ByteBuf buffer) throws CipResponseException, PartialResponseException {
        MessageRouterResponse response = MessageRouterResponse.decode(buffer);

        if (response.getGeneralStatus() == 0x00) {
            return response.getData();
        } else {
            throw new CipResponseException(response.getGeneralStatus(), response.getAdditionalStatus());
        }
    }

}
