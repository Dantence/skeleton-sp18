/**
 * @author Dantence
 * @description:
 * @date 2022/12/31
 */

import java.math.*;

/**
 * @projectName: proj0
 * @package: PACKAGE_NAME
 * @className: Planet
 * @author: Dantence
 * @description: TODO
 * @date: 2022/12/31 17:58
 * @version: 1.0
 */
public class Planet {
    /**
     * Its current x position
     */
    public double xxPos;

    /**
     * Its current y position
     */
    public double yyPos;

    /**
     * Its current velocity in the x position
     */
    public double xxVel;

    /**
     * Its current velocity in the y position
     */
    public double yyVel;

    /**
     * Its mass
     */
    public double mass;

    /**
     * The name of the file that corresponds to the image that depicts the planet
     */
    public String imgFileName;

    /**
     * The gravitational constant
     */
    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.imgFileName = p.imgFileName;
        this.mass = p.mass;
    }

    public double calcDistance(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);
        return G * this.mass * p.mass / (r * r);
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double r = this.calcDistance(p);
        double F = this.calcForceExertedBy(p);
        return F * dx / r;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - this.yyPos;
        double r = this.calcDistance(p);
        double F = this.calcForceExertedBy(p);
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double Fx = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) {
                Fx += this.calcForceExertedByX(planet);
            }
        }
        return Fx;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double Fy = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) {
                Fy += this.calcForceExertedByY(planet);
            }
        }
        return Fy;
    }

    public void update(double dt, double Fx, double Fy) {
        double ax = Fx / this.mass;
        double ay = Fy / this.mass;
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
