package ndk.kada;

import ndk.utils_android14.ApplicationActivity;

public class KadaActivity extends ApplicationActivity {

   KadaSpecification kadaSpecification=new KadaSpecification();

   @Override
   public String configureApplicationTag() {

      return kadaSpecification.applicationName;
   }
}
