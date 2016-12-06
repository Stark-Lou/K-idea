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
package whu.ts.math.distribution;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

import java.io.Serializable;

/**
 * Interface for distributions with breakpoints.
 *
 * @since 1.0
 */
public interface DistributionDivider extends Serializable {
    /**
     * Get the breakpoints for dividing the distribution in equal areas of probability.
     * NOTE: the breakpoints are in ascending order.
     *
     * @param areas the number of areas
     * @return the list of breakpoints
     * @throws NumberIsTooSmallException if areas is smaller than 2
     */
    double[] getBreakpoints(int areas);
}
