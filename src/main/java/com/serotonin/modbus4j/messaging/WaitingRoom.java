//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.serotonin.modbus4j.messaging;

import com.serotonin.messaging.IncomingResponseMessage;
import com.serotonin.messaging.WaitingRoomException;
import com.serotonin.messaging.WaitingRoomKey;

import java.util.HashMap;
import java.util.Map;

class WaitingRoom {
    private final Map<WaitingRoomKey, Member> waitHere = new HashMap();

    WaitingRoom() {
    }

    void enter(WaitingRoomKey key) throws WaitingRoomException {
        Member member = new Member();
        synchronized(this) {
            Member dup = (Member)this.waitHere.get(key);
            if (dup != null) {
                throw new WaitingRoomException("Waiting room too crowded. Already contains the key " + key);
            } else {
                this.waitHere.put(key, member);
            }
        }
    }

    IncomingResponseMessage getResponse(WaitingRoomKey key, long timeout) throws WaitingRoomException {
        Member member;
        synchronized(this) {
            member = (Member)this.waitHere.get(key);
        }

        if (member == null) {
            throw new WaitingRoomException("No member for key " + key);
        } else {
            return member.getResponse(timeout);
        }
    }

    void leave(WaitingRoomKey key) {
        synchronized(this) {
            this.waitHere.remove(key);
        }
    }

    void response(IncomingResponseMessage response) throws WaitingRoomException {
        WaitingRoomKey key = response.getWaitingRoomKey();
        Member member;
        synchronized(this) {
            member = (Member)this.waitHere.get(key);
        }

        if (member != null) {
            member.setResponse(response);
        } else {
           // throw new WaitingRoomException("No recipient was found waiting for response for key " + key);
        }
    }

    class Member {
        private IncomingResponseMessage response;

        Member() {
        }

        synchronized void setResponse(IncomingResponseMessage response) {
            this.response = response;
            this.notify();
        }

        synchronized IncomingResponseMessage getResponse(long timeout) {
            if (this.response != null) {
                return this.response;
            } else {
                this.waitNoThrow(timeout);
                return this.response;
            }
        }

        private void waitNoThrow(long timeout) {
            try {
                this.wait(timeout);
            } catch (InterruptedException var4) {

            }

        }
    }
}
