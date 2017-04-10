package box24.com.box24sdk.model;

import java.io.Serializable;

/**
 * Created by ERROR on 12/25/2014.
 */
public class LocationBox24  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public String location_id;
	public String center_location;

	public String location_name_for_api_use;
    public String location_condo_name_for_api_use;
    public String location_address_for_api_use;
    public String location_image;
    public String location_tel_for_api_use;
    public String location_avilable_locker;
    public String location_avilable_status;
    public double latitude;
    public double longitude;
    public int fav;
}
