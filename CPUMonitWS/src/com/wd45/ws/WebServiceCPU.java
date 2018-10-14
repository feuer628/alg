package com.wd45.ws;
import javax.jws.WebMethod;
        import javax.jws.WebService;
        import javax.jws.soap.SOAPBinding;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import java.io.IOException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WebServiceCPU {

    @WebMethod
    byte[] getCPULoad() throws MalformedObjectNameException, ReflectionException, InstanceNotFoundException, IOException;
}