package com.vividsolutions.jts.algorithm;

public class RobustDeterminant {
    public static int signOfDet2x2(double x1, double y1, double x2, double y2) {
        long count = 0;
        int sign = 1;
        if (x1 == 0.0d || y2 == 0.0d) {
            if (y1 == 0.0d || x2 == 0.0d) {
                return 0;
            }
            if (y1 > 0.0d) {
                return x2 > 0.0d ? -1 : 1;
            } else {
                if (x2 > 0.0d) {
                    return 1;
                }
                return -1;
            }
        } else if (y1 != 0.0d && x2 != 0.0d) {
            double swap;
            if (0.0d < y1) {
                if (0.0d < y2) {
                    if (y1 > y2) {
                        sign = -1;
                        swap = x1;
                        x1 = x2;
                        x2 = swap;
                        swap = y1;
                        y1 = y2;
                        y2 = swap;
                    }
                } else if (y1 <= (-y2)) {
                    sign = -1;
                    x2 = -x2;
                    y2 = -y2;
                } else {
                    swap = x1;
                    x1 = -x2;
                    x2 = swap;
                    swap = y1;
                    y1 = -y2;
                    y2 = swap;
                }
            } else if (0.0d < y2) {
                if ((-y1) <= y2) {
                    sign = -1;
                    x1 = -x1;
                    y1 = -y1;
                } else {
                    swap = -x1;
                    x1 = x2;
                    x2 = swap;
                    swap = -y1;
                    y1 = y2;
                    y2 = swap;
                }
            } else if (y1 >= y2) {
                x1 = -x1;
                y1 = -y1;
                x2 = -x2;
                y2 = -y2;
            } else {
                sign = -1;
                swap = -x1;
                x1 = -x2;
                x2 = swap;
                swap = -y1;
                y1 = -y2;
                y2 = swap;
            }
            if (0.0d < x1) {
                if (0.0d >= x2) {
                    return sign;
                }
                if (x1 > x2) {
                    return sign;
                }
            } else if (0.0d < x2) {
                return -sign;
            } else {
                if (x1 < x2) {
                    return -sign;
                }
                sign = -sign;
                x1 = -x1;
                x2 = -x2;
            }
            do {
                count++;
                double k = Math.floor(x2 / x1);
                x2 -= k * x1;
                y2 -= k * y1;
                if (y2 < 0.0d) {
                    return -sign;
                }
                if (y2 > y1) {
                    return sign;
                }
                if (x1 > x2 + x2) {
                    if (y1 < y2 + y2) {
                        return sign;
                    }
                } else if (y1 > y2 + y2) {
                    return -sign;
                } else {
                    x2 = x1 - x2;
                    y2 = y1 - y2;
                    sign = -sign;
                }
                if (y2 == 0.0d) {
                    if (x2 == 0.0d) {
                        return 0;
                    }
                    return -sign;
                } else if (x2 == 0.0d) {
                    return sign;
                } else {
                    k = Math.floor(x1 / x2);
                    x1 -= k * x2;
                    y1 -= k * y2;
                    if (y1 < 0.0d) {
                        return sign;
                    }
                    if (y1 > y2) {
                        return -sign;
                    }
                    if (x2 > x1 + x1) {
                        if (y2 < y1 + y1) {
                            return -sign;
                        }
                    } else if (y2 > y1 + y1) {
                        return sign;
                    } else {
                        x1 = x2 - x1;
                        y1 = y2 - y1;
                        sign = -sign;
                    }
                    if (y1 == 0.0d) {
                        return x1 == 0.0d ? 0 : sign;
                    }
                }
            } while (x1 != 0.0d);
            return -sign;
        } else if (y2 <= 0.0d) {
            return x1 > 0.0d ? -1 : 1;
        } else {
            if (x1 > 0.0d) {
                return 1;
            }
            return -1;
        }
    }
}
