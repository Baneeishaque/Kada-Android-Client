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

    String getInsertUserAccountApiUrl() {

        return getApiMethodEndpointUrl("insertUserAccount");
    }

    String getInsertShopApiUrl() {

        return getApiMethodEndpointUrl("insertShop");
    }

    String getInsertShopItemApiUrl() {

        return getApiMethodEndpointUrl("insertShopItem");
    }

    String getShopCategoriesApiUrl() {

        return getApiMethodEndpointUrl("getShopCategories");
    }

    String getShopForUserApiUrl() {

        return getApiMethodEndpointUrl("getShopForUser");
    }

    String getCategoriesForShopApiUrl() {

        return getApiMethodEndpointUrl("getCategoriesForShop");
    }

    String getUserForMobileNumberApiUrl() {

        return getApiMethodEndpointUrl("getUserForMobileNumber");
    }
}
