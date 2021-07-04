package ndk.kada;

public class KadaApiUtils extends ApiUtils {

    @Override
    protected String getServerApplicationHttpApiFolder() {

        return ServerEndPoint.serverApplicationHttpApiFolder;
    }

    @Override
    protected String getFileExtension() {

        return ServerEndPoint.serverFileExtension;
    }

    @Override
    protected String getServerApplicationFolder() {

        return ServerEndPoint.serverApplicationFolder;
    }

    @Override
    protected String getServerAddress() {

        return ServerEndPoint.serverAddress;
    }

    @Override
    protected String getAddressProtocol() {

        return ServerEndPoint.serverAddressProtocol;
    }

//    String getLoginApiUrl() {
//
////        return getApiMethodEndpointUrl("login.php");
//        return getApiMethodEndpointUrl("getLogin");
//    }

//    String getSignUpApiUrl() {
//
//        return getApiMethodEndpointUrl("signup.php");
//    }

//    String getGroupsApiUrl() {
//
//        return getApiMethodEndpointUrl("getGroups");
//    }

    String getInsertUserAccountApiUrl() {

        return getApiMethodEndpointUrl("insertUserAccount");
    }

    String getInsertShopApiUrl() {

        return getApiMethodEndpointUrl("insertShop");
    }

    String getShopCategoriesApiUrl() {

        return getApiMethodEndpointUrl("getShopCategories");
    }
}
