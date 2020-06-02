//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.serotonin.modbus4j.ip.xa;

import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpMessageResponse;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.util.queue.ByteQueue;

public class XaMessageResponse extends XaMessage implements IpMessageResponse {
    static XaMessageResponse createXaMessageResponse(ByteQueue queue) throws ModbusTransportException {
        int transactionId = ModbusUtils.popShort(queue);
        int protocolId = ModbusUtils.popShort(queue);
        //if (protocolId != 0) {
            //throw new ModbusTransportException("Unsupported IP protocol id: " + protocolId);
       // } else {
            ModbusUtils.popShort(queue);
            ModbusResponse response = ModbusResponse.createModbusResponse(queue);
            return new XaMessageResponse(response, transactionId);
       // }
    }

    public XaMessageResponse(ModbusResponse modbusResponse, int transactionId) {
        super(modbusResponse, transactionId);
    }

    public ModbusResponse getModbusResponse() {
        return (ModbusResponse)this.modbusMessage;
    }
}
