import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    private static final int MAX_DEPTH = 7;
    private static final double rootLonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;

    private double getMinLonDPP() {
        return rootLonDPP / 128;
    }

    private int getDepthByLonDPP(double LonDPP) {
        if (LonDPP <= getMinLonDPP()) {
            return MAX_DEPTH;
        }
        int depth = 0;
        double testLonDPP = rootLonDPP;
        while (testLonDPP > LonDPP) {
            depth ++;
            testLonDPP /= 2;
        }
        return depth;
    }

    private int getGridNumByDepth(int depth) {
        int grid_num = 1;
        for(int i = 0; i < depth; i++) {
            grid_num *= 2;
        }
        return grid_num;
    }

    private double getLatPerGrid(int grid_num) {
        return (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / grid_num;
    }

    private double getLonPerGrid(int grid_num) {
        return (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / grid_num;
    }

    private Map<String, Object> getRenderGrid(int depth, double ullon, double ullat, double lrlon, double lrlat) {
        int grid_num = getGridNumByDepth(depth);
        boolean query_success = true;
        double lon_per_grid = getLonPerGrid(grid_num);
        double lat_per_grid = getLatPerGrid(grid_num);
        int start_x = (int) ((ullon - MapServer.ROOT_ULLON) / lon_per_grid);
        int start_y = (int) ((MapServer.ROOT_ULLAT - ullat) / lat_per_grid);
        int end_x = (int) ((lrlon - MapServer.ROOT_ULLON) / lon_per_grid);
        int end_y = (int) ((MapServer.ROOT_ULLAT - lrlat) / lat_per_grid);
        end_x = Math.min(grid_num - 1, end_x);
        end_y = Math.min(grid_num - 1, end_y);

        String [][] render_grid;
        if(start_x <= end_x && start_y <= end_y) {
            render_grid = new String[end_y - start_y + 1][end_x - start_x + 1];
        } else {
            query_success = false;
            render_grid = null;
        }

        for(int y = start_y; y <= end_y; y++) {
            for(int x = start_x; x <= end_x; x++) {
                render_grid[y - start_y][x - start_x] = "d" + depth + "_" + "x" + x + "_" + "y" + y + ".png";
            }
        }
        double raster_ul_lon = MapServer.ROOT_ULLON + start_x * lon_per_grid;
        double raster_ul_lat = MapServer.ROOT_ULLAT - start_y * lat_per_grid;
        double raster_lr_lon = MapServer.ROOT_ULLON + (end_x + 1) * lon_per_grid;
        double raster_lr_lat = MapServer.ROOT_ULLAT - (end_y + 1) * lat_per_grid;
        Map<String, Object> res = new HashMap<>();
        res.put("raster_ul_lon", raster_ul_lon);
        res.put("raster_ul_lat", raster_ul_lat);
        res.put("raster_lr_lon", raster_lr_lon);
        res.put("raster_lr_lat", raster_lr_lat);
        res.put("render_grid", render_grid);
        res.put("query_success", query_success);
        return res;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double width = params.get("w");
        double height = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");

        double LonDPP = (lrlon - ullon) / width;
        int depth = getDepthByLonDPP(LonDPP);

//        System.out.println("LonDPP: " + LonDPP);
//        System.out.println("rootLonDPP: " + rootLonDPP);
//        System.out.println("minLonDPP: " + getMinLonDPP());
//        System.out.println("depth: " + depth);

        Map<String, Object> results = new HashMap<>();
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        Map<String, Object> res = getRenderGrid(depth, ullon, ullat, lrlon, lrlat);
        results.put("depth", depth);
        results.put("render_grid", res.get("render_grid"));
        results.put("raster_ul_lon", res.get("raster_ul_lon"));
        results.put("raster_lr_lon", res.get("raster_lr_lon"));
        results.put("raster_lr_lat", res.get("raster_lr_lat"));
        results.put("raster_ul_lat", res.get("raster_ul_lat"));
        results.put("query_success", res.get("query_success"));
        return results;
    }

}
