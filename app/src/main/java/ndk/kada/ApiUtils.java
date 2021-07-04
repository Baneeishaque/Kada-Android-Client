package ndk.kada;

public abstract class ApiUtils {

//    public String getApiMethodEndpointUrl(String apiMethodName) {
//
//        return getAddressProtocol() + "://" + getServerAddress() + "/" + getServerApplicationFolder() + "/" + apiMethodName;
//    }

//    public String getApiMethodEndpointUrl(String apiMethodName) {
//
//        return getAddressProtocol() + "://" + getServerAddress() + "/" + getServerApplicationFolder() + "/" + apiMethodName + getFileExtension();
//    }

    public String getApiMethodEndpointUrl(String apiMethodName) {

        return getAddressProtocol() + "://" + getServerAddress() + "/" + getServerApplicationFolder() + "/" + getServerApplicationHttpApiFolder() + "/" + apiMethodName + getFileExtension();
    }

    protected abstract String getServerApplicationHttpApiFolder();

    protected abstract String getFileExtension();

    protected abstract String getServerApplicationFolder();

    protected abstract String getServerAddress();

    protected abstract String getAddressProtocol();
}
