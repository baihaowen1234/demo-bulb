//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.serotonin.modbus4j.msg;

import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.code.ExceptionCode;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.util.queue.ByteQueue;

public abstract class ModbusResponse extends ModbusMessage {
    protected static final byte MAX_FUNCTION_CODE = -128;
    protected byte exceptionCode = -1;

    public static ModbusResponse createModbusResponse(ByteQueue queue) throws ModbusTransportException {
        int slaveId = ModbusUtils.popUnsignedByte(queue);
        byte functionCode = queue.pop();
        boolean isException = false;
        if (greaterThan(functionCode, (byte)-128)) {
            isException = true;
            functionCode = (byte)(functionCode + 128);
        }

        ModbusResponse response = null;

            if (functionCode == 1) {
                response = new ReadCoilsResponse(slaveId);
            } else if (functionCode == 2) {
                response = new ReadDiscreteInputsResponse(slaveId);
            } else if (functionCode == 3) {
                response = new ReadHoldingRegistersResponse(slaveId);
            } else if (functionCode == 4) {
                response = new ReadInputRegistersResponse(slaveId);
            } else if (functionCode == 5) {
                response = new WriteCoilResponse(slaveId);
            } else if (functionCode == 6) {
                response = new WriteRegisterResponse(slaveId);
            } else if (functionCode == 7) {
                response = new ReadExceptionStatusResponse(slaveId);
            } else if (functionCode == 15) {
                response = new WriteCoilsResponse(slaveId);
            } else if (functionCode == 16) {
                response = new WriteRegistersResponse(slaveId);
            } else if (functionCode == 17) {
                response = new ReportSlaveIdResponse(slaveId);
            } else {
                if (functionCode != 22) {
                    //throw new IllegalFunctionException(functionCode, slaveId);
                    response = new ReadHoldingRegistersResponse(slaveId);
                }

                response = new WriteMaskRegisterResponse(slaveId);
            }



        ((ModbusResponse)response).read(queue, isException);
        return (ModbusResponse)response;
    }

    ModbusResponse(int slaveId) throws ModbusTransportException {
        super(slaveId);
    }

    public boolean isException() {
        return this.exceptionCode != -1;
    }

    public String getExceptionMessage() {
        return ExceptionCode.getExceptionMessage(this.exceptionCode);
    }

    void setException(byte exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public byte getExceptionCode() {
        return this.exceptionCode;
    }

    protected final void writeImpl(ByteQueue queue) {
        if (this.isException()) {
            queue.push((byte)(this.getFunctionCode() + -128));
            queue.push(this.exceptionCode);
        } else {
            queue.push(this.getFunctionCode());
            this.writeResponse(queue);
        }

    }

    protected abstract void writeResponse(ByteQueue var1);

    void read(ByteQueue queue, boolean isException) {
        if (isException) {
            this.exceptionCode = queue.pop();
        } else {
            this.readResponse(queue);
        }

    }

    protected abstract void readResponse(ByteQueue var1);

    private static boolean greaterThan(byte b1, byte b2) {
        int i1 = b1 & 255;
        int i2 = b2 & 255;
        return i1 > i2;
    }

    public static void main(String[] args) throws Exception {
        ByteQueue queue = new ByteQueue(new byte[]{3, 2});
        ModbusResponse r = createModbusResponse(queue);
        System.out.println(r);
    }
}
