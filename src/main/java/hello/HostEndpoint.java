package hello;


import io.spring.guides.gs_producing_web_service.GetHostRequest;
import io.spring.guides.gs_producing_web_service.GetHostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Mindaugas Vidmantas on 2016-06-06.
 * email: minvidm@gmail.com, minvidm@ktu.lt
 */


@Endpoint
public class HostEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private HostRepository hostRepository;

    @Autowired
    public HostEndpoint(HostRepository countryRepository) {
        this.hostRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHostRequest")
    @ResponsePayload
    public GetHostResponse getHost(@RequestPayload GetHostRequest request) {
        GetHostResponse response = new GetHostResponse();
        response.setHost(hostRepository.findHost(request.getName()));

        return response;
    }
}