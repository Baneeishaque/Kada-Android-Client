package ndk.kada.utils;

import ndk.kada.KadaServerEndPoint;
import ndk.utils_android1.ApiUtilsWithHttpApiFolderAndFileExtension1;

public class KadaApiUtils extends ApiUtilsWithHttpApiFolderAndFileExtension1 {

    @Override
    protected String getServerApplicationHttpApiFolder() {

        return KadaServerEndPoint.serverApplicationHttpApiFolder;
    }

    @Override
    protected String getFileExtension() {

        return KadaServerEndPoint.serverFileExtension;
    }

    @Override
    protected String getServerApplicationFolder() {

        return KadaServerEndPoint.serverApplicationFolder;
    }

    @Override
    protected String getServerAddress() {

        return KadaServerEndPoint.serverAddress;
    }

    @Override
    protected String getAddressProtocol() {

        return KadaServerEndPoint.serverAddressProtocol;
    }

    public String getInsertUserAccountApiUrl() {

        return getApiMethodEndpointUrl("insertUserAccount");
    }

    public String getInsertShopApiUrl() {

        return getApiMethodEndpointUrl("insertShop");
    }

    public String getInsertShopItemApiUrl() {

        return getApiMethodEndpointUrl("insertShopItem");
    }

    public String getUpdateStockForItemApiUrl() {

        return getApiMethodEndpointUrl("updateStockForItem");
    }

    public String getShopCategoriesApiUrl() {

        return getApiMethodEndpointUrl("getShopCategories");
    }

    public String getShopsApiUrl() {

        return getApiMethodEndpointUrl("getShops");
    }

    public String getShopForUserApiUrl() {

        return getApiMethodEndpointUrl("getShopForUser");
    }

    public String getCategoriesForShopApiUrl() {

        return getApiMethodEndpointUrl("getCategoriesForShop");
    }

    public String getItemsForShopApiUrl() {

        return getApiMethodEndpointUrl("getItemsForShop");
    }

    public String getUserForMobileNumberApiUrl() {

        return getApiMethodEndpointUrl("getUserForMobileNumber");
    }
}
