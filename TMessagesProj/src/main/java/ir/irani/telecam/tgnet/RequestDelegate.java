package ir.irani.telecam.tgnet;

public interface RequestDelegate {
    void run(TLObject response, TLRPC.TL_error error);
}
