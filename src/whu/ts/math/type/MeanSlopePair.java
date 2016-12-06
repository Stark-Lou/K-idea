/*
 * Copyright 2015 Octavian Hasna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package whu.ts.math.type;

import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;
import whu.ts.math.util.TimeSeriesPrecision;

/**
 * @since 1.0
 */
public class MeanSlopePair extends Pair<Double, Double> {

    /**
     * Create an entry of type (mean, slope).
     *
     * @param mean  the mean value for the segment
     * @param slope the slope of the segment
     */
    public MeanSlopePair(double mean, double slope) {
        super(mean, slope);
    }

    public double getMean() {
        return getFirst();
    }

    public double getSlope() {
        return getSecond();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeanSlopePair)) {
            return false;
        } else {
            MeanSlopePair msp = (MeanSlopePair) o;
            return Precision.equals(getFirst(), msp.getMean(), TimeSeriesPrecision.EPSILON) &&
                    Precision.equals(getSecond(), msp.getSlope(), TimeSeriesPrecision.EPSILON);
        }
    }
}
