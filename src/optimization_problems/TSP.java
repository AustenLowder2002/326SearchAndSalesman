package optimization_problems;

import core_algorithms.Problem;

import java.util.*;

import static java.lang.System.exit;

/**
 * A formulation of the travelling salesperson problem
 * A state is a tour of all cities in the MAP
 *
 * The samples are adopted with some modifications from:
 * <a href="https://github.com/asilichenko/simulated-annealing/">...</a>
 *
 */
public class TSP implements Problem<List<Integer>> {

    private final Sample MAP;
    private final List<Integer> INIT_STATE = new ArrayList<>();

    public TSP(int size){
        switch (size) {
            case 5:
                this.MAP = Sample.SAMPLE_5;
                break;
            case 6:
                this.MAP = Sample.SAMPLE_6;
                break;
            case 17:
                this.MAP = Sample.SAMPLE_17;
                break;
            case 26:
                this.MAP = Sample.SAMPLE_26;
                break;
            default:
                //just to make Java happy...
                this.MAP = null;
                System.out.println("Wrong map size; Choose from: 5, 6, 17, or 26.");
                exit(1);
        }
        for(int i=0; i<size; i++) {
            INIT_STATE.add(i);
        }
    }

    //generate a new tour by randomly swap two cities in the given tour
    public List<Integer> generateNewState (List<Integer> state){
        Random r = new Random();
        int city1 = r.nextInt(state.size());
        int city2;
        do {
            city2 = r.nextInt(state.size());
        }while(city2 == city1);
        List<Integer> newState = new ArrayList<>(state);
        Collections.swap(newState, city1, city2);
        return newState;
    }

    public double cost(List<Integer> state){
        double totalDistance = 0.0;
        int size = state.size();

        // Check if the state list is empty or has only one city
        if (size <= 1) {
            System.err.println("Error: State list is empty or has only one city.");
            return 0.0;
        }

        for(int i = 0, j = 1; j < size; i++, j++) {
            int cityIndex1 = state.get(i);
            int cityIndex2 = state.get(j);

            // Check if the city indices are within the bounds of the distance matrix
            if (cityIndex1 < 0 || cityIndex1 >= MAP.distanceMatrix.length ||
                    cityIndex2 < 0 || cityIndex2 >= MAP.distanceMatrix.length) {
                return Double.POSITIVE_INFINITY;
            }

            // Calculate the distance between the two cities
            double distance = MAP.distanceMatrix[cityIndex1][cityIndex2];

            // Check if the distance is zero
            if (distance == 0.0) {
                System.err.println("Error: Distance between city " + cityIndex1 + " and city " + cityIndex2 + " is zero.");
                return Double.POSITIVE_INFINITY; // Return infinity to indicate an error
            }

            totalDistance += distance;

        }

        return totalDistance;
    }


    public List<Integer> getInitState() {
        return INIT_STATE;
    }

    //In the distance matrix, the indices represents cities
    //e.g., distanceMatrix[1][4] specifies the distance between
    //city 1 and city 4.
    public record Sample(int[][] distanceMatrix) {
        public static final Sample SAMPLE_5 = new Sample(
                new int[][]{
                        {0, 3, 4, 2, 7},
                        {3, 0, 4, 6, 3},
                        {4, 4, 0, 5, 8},
                        {2, 6, 5, 0, 6},
                        {7, 3, 8, 6, 0},
                });

        public static final Sample SAMPLE_6 = new Sample(
                new int[][]{
                        {0, 64, 37, 51, 43, 40},
                        {64, 0, 31, 45, 37, 16},
                        {37, 31, 0, 17, 26, 34},
                        {51, 45, 17, 0, 42, 42},
                        {43, 37, 26, 42, 0, 27},
                        {40, 16, 34, 42, 27, 0},
                });

        //shortest distance: 2085
        public static final Sample SAMPLE_17 = new Sample(
                new int[][]{
                        {0, 633, 257, 91, 412, 150, 80, 134, 259, 505, 353, 324, 70, 211, 268, 246, 121},
                        {633, 0, 390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518},
                        {257, 390, 0, 228, 169, 112, 196, 154, 372, 262, 110, 437, 191, 74, 53, 472, 142},
                        {91, 661, 228, 0, 383, 120, 77, 105, 175, 476, 324, 240, 27, 182, 239, 237, 84},
                        {412, 227, 169, 383, 0, 267, 351, 309, 338, 196, 61, 421, 346, 243, 199, 528, 297},
                        {150, 488, 112, 120, 267, 0, 63, 34, 264, 360, 208, 329, 83, 105, 123, 364, 35},
                        {80, 572, 196, 77, 351, 63, 0, 29, 232, 444, 292, 297, 47, 150, 207, 332, 29},
                        {134, 530, 154, 105, 309, 34, 29, 0, 249, 402, 250, 314, 68, 108, 165, 349, 36},
                        {259, 555, 372, 175, 338, 264, 232, 249, 0, 495, 352, 95, 189, 326, 383, 202, 236},
                        {505, 289, 262, 476, 196, 360, 444, 402, 495, 0, 154, 578, 439, 336, 240, 685, 390},
                        {353, 282, 110, 324, 61, 208, 292, 250, 352, 154, 0, 435, 287, 184, 140, 542, 238},
                        {324, 638, 437, 240, 421, 329, 297, 314, 95, 578, 435, 0, 254, 391, 448, 157, 301},
                        {70, 567, 191, 27, 346, 83, 47, 68, 189, 439, 287, 254, 0, 145, 202, 289, 55},
                        {211, 466, 74, 182, 243, 105, 150, 108, 326, 336, 184, 391, 145, 0, 57, 426, 96},
                        {268, 420, 53, 239, 199, 123, 207, 165, 383, 240, 140, 448, 202, 57, 0, 483, 153},
                        {246, 745, 472, 237, 528, 364, 332, 349, 202, 685, 542, 157, 289, 426, 483, 0, 336},
                        {121, 518, 142, 84, 297, 35, 29, 36, 236, 390, 238, 301, 55, 96, 153, 336, 0},
                });

        //shortest distance: 937
        public static final Sample SAMPLE_26 = new Sample(
                new int[][]{
                        {0, 83, 93, 129, 133, 139, 151, 169, 135, 114, 110, 98, 99, 95, 81, 152, 159, 181, 172, 185, 147, 157, 185, 220, 127, 181},
                        {83, 0, 40, 53, 62, 64, 91, 116, 93, 84, 95, 98, 89, 68, 67, 127, 156, 175, 152, 165, 160, 180, 223, 268, 179, 197},
                        {93, 40, 0, 42, 42, 49, 59, 81, 54, 44, 58, 64, 54, 31, 36, 86, 117, 135, 112, 125, 124, 147, 193, 241, 157, 161},
                        {129, 53, 42, 0, 11, 11, 46, 72, 65, 70, 88, 100, 89, 66, 76, 102, 142, 156, 127, 139, 155, 180, 228, 278, 197, 190},
                        {133, 62, 42, 11, 0, 9, 35, 61, 55, 62, 82, 95, 84, 62, 74, 93, 133, 146, 117, 128, 148, 173, 222, 272, 194, 182},
                        {139, 64, 49, 11, 9, 0, 39, 65, 63, 71, 90, 103, 92, 71, 82, 100, 141, 153, 124, 135, 156, 181, 230, 280, 202, 190},
                        {151, 91, 59, 46, 35, 39, 0, 26, 34, 52, 71, 88, 77, 63, 78, 66, 110, 119, 88, 98, 130, 156, 206, 257, 188, 160},
                        {169, 116, 81, 72, 61, 65, 26, 0, 37, 59, 75, 92, 83, 76, 91, 54, 98, 103, 70, 78, 122, 148, 198, 250, 188, 148},
                        {135, 93, 54, 65, 55, 63, 34, 37, 0, 22, 39, 56, 47, 40, 55, 37, 78, 91, 62, 74, 96, 122, 172, 223, 155, 128},
                        {114, 84, 44, 70, 62, 71, 52, 59, 22, 0, 20, 36, 26, 20, 34, 43, 74, 91, 68, 82, 86, 111, 160, 210, 136, 121},
                        {110, 95, 58, 88, 82, 90, 71, 75, 39, 20, 0, 18, 11, 27, 32, 42, 61, 80, 64, 77, 68, 92, 140, 190, 116, 103},
                        {98, 98, 64, 100, 95, 103, 88, 92, 56, 36, 18, 0, 11, 34, 31, 56, 63, 85, 75, 87, 62, 83, 129, 178, 100, 99},
                        {99, 89, 54, 89, 84, 92, 77, 83, 47, 26, 11, 11, 0, 23, 24, 53, 68, 89, 74, 87, 71, 93, 140, 189, 111, 107},
                        {95, 68, 31, 66, 62, 71, 63, 76, 40, 20, 27, 34, 23, 0, 15, 62, 87, 106, 87, 100, 93, 116, 163, 212, 132, 130},
                        {81, 67, 36, 76, 74, 82, 78, 91, 55, 34, 32, 31, 24, 15, 0, 73, 92, 112, 96, 109, 93, 113, 158, 205, 122, 130},
                        {152, 127, 86, 102, 93, 100, 66, 54, 37, 43, 42, 56, 53, 62, 73, 0, 44, 54, 26, 39, 68, 94, 144, 196, 139, 95},
                        {159, 156, 117, 142, 133, 141, 110, 98, 78, 74, 61, 63, 68, 87, 92, 44, 0, 22, 34, 38, 30, 53, 102, 154, 109, 51},
                        {181, 175, 135, 156, 146, 153, 119, 103, 91, 91, 80, 85, 89, 106, 112, 54, 22, 0, 33, 29, 46, 64, 107, 157, 125, 51},
                        {172, 152, 112, 127, 117, 124, 88, 70, 62, 68, 64, 75, 74, 87, 96, 26, 34, 33, 0, 13, 63, 87, 135, 186, 141, 81},
                        {185, 165, 125, 139, 128, 135, 98, 78, 74, 82, 77, 87, 87, 100, 109, 39, 38, 29, 13, 0, 68, 90, 136, 186, 148, 79},
                        {147, 160, 124, 155, 148, 156, 130, 122, 96, 86, 68, 62, 71, 93, 93, 68, 30, 46, 63, 68, 0, 26, 77, 128, 80, 37},
                        {157, 180, 147, 180, 173, 181, 156, 148, 122, 111, 92, 83, 93, 116, 113, 94, 53, 64, 87, 90, 26, 0, 50, 102, 65, 27},
                        {185, 223, 193, 228, 222, 230, 206, 198, 172, 160, 140, 129, 140, 163, 158, 144, 102, 107, 135, 136, 77, 50, 0, 51, 64, 58},
                        {220, 268, 241, 278, 272, 280, 257, 250, 223, 210, 190, 178, 189, 212, 205, 196, 154, 157, 186, 186, 128, 102, 51, 0, 93, 107},
                        {127, 179, 157, 197, 194, 202, 188, 188, 155, 136, 116, 100, 111, 132, 122, 139, 109, 125, 141, 148, 80, 65, 64, 93, 0, 90},
                        {181, 197, 161, 190, 182, 190, 160, 148, 128, 121, 103, 99, 107, 130, 130, 95, 51, 51, 81, 79, 37, 27, 58, 107, 90, 0},
                });
    }

}
