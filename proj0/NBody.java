/**
 * @author Dantence
 * @description:
 * @date 2022/12/31
 */

/**
 * @projectName: proj0
 * @package: PACKAGE_NAME
 * @className: NBody
 * @author: Dantence
 * @description: TODO
 * @date: 2022/12/31 20:07
 * @version: 1.0
 */
public class NBody {
    private static final String imageToDraw = "images/starfield.jpg";

    public static double readRadius(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int planetsNum = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String planetsTxtPath) {
        In in = new In(planetsTxtPath);
        int planetsNum = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[planetsNum];
        for (int i = 0; i < planetsNum; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String planetFileName = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, planetFileName);
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, imageToDraw);
        StdDraw.show();
        for (Planet planet : planets) {
            planet.draw();
        }
        StdDraw.enableDoubleBuffering();
        double t = 0;
        while (t <= T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0, 0, imageToDraw);
            for (int i = 0; i < planets.length; i++) {
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += 1;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
