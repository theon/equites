// Equites, a simple chess interface
// Copyright © 2011-2013 Frank S. Thomas <f.thomas@gmx.de>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package eu.timepit.equites

import org.specs2.mutable._

import Square._

class SquareSpec extends Specification {
  "Square companion" should {
    "correctly perform validCoordinates" in {
      validCoordinates( 4,  4) must beTrue
      validCoordinates( 0,  0) must beTrue
      validCoordinates( 7,  7) must beTrue
      validCoordinates(-1,  4) must beFalse
      validCoordinates( 1,  8) must beFalse
      validCoordinates( 1, -2) must beFalse
    }

    "correctly perform validSum" in {
      validSum(Square(0, 0), Vec( 1,  1)) must beTrue
      validSum(Square(1, 1), Vec(-1, -1)) must beTrue
      validSum(Square(0, 0), Vec(-1, -1)) must beFalse
      validSum(Square(7, 7), Vec( 1,  1)) must beFalse
      validSum(Square(7, 7), Vec( 1,  1)) must beFalse
    }

    "correctly calculate l1Dist and lInfDist" in {
      val s1 = Square(1, 2)
      val s2 = Square(4, 3)

      l1Dist(s1, s2)   must_== 4
      lInfDist(s1, s2) must_== 3

      val s3 = Square(0, 0)
      val s4 = Square(1, 1)

      l1Dist(s3, s4)   must_== 2
      lInfDist(s3, s4) must_== 1
    }
  }

  "Square" should {
    "throw an exception on invalid values" in {
      Square(-1,  0) must throwAn[IllegalArgumentException]
      Square( 0, -1) must throwAn[IllegalArgumentException]
      Square(-1, -1) must throwAn[IllegalArgumentException]
      Square( 8,  0) must throwAn[IllegalArgumentException]
      Square( 0,  8) must throwAn[IllegalArgumentException]
      Square( 8,  8) must throwAn[IllegalArgumentException]
    }

    "correctly perform +(Vec) and -(Vec)" in {
      Square(1, 1) + Vec( 1,  1) must_== Square(2, 2)
      Square(1, 1) - Vec(-1, -1) must_== Square(2, 2)
      Square(1, 1) - Vec( 1,  1) must_== Square(0, 0)
      Square(1, 1) + Vec(-1, -1) must_== Square(0, 0)

      Square(0, 0) - Vec(1, 1) must throwAn[IllegalArgumentException]
      Square(7, 7) + Vec(1, 1) must throwAn[IllegalArgumentException]
    }

    "correctly perform +(Square)" in {
      Square(1, 1) + Square(1, 1) must_== Vec(2, 2)
      Square(2, 2) + Square(1, 1) must_== Vec(3, 3)
      Square(2, 1) + Square(0, 0) must_== Vec(2, 1)
      Square(0, 0) + Square(3, 4) must_== Vec(3, 4)
    }

    "correctly perform -(Square)" in {
      Square(1, 1) - Square(1, 1) must_== Vec(0, 0)
      Square(2, 2) - Square(1, 1) must_== Vec(1, 1)
      Square(2, 1) - Square(0, 0) must_== Vec(2, 1)
      Square(0, 0) - Square(3, 4) must_== Vec(-3, -4)
    }

    "correctly perform isLight and isDark" in {
      Square(0, 0).isDark must beTrue
      Square(7, 7).isDark must beTrue
      Square(0, 2).isDark must beTrue
      Square(2, 0).isDark must beTrue

      Square(0, 1).isLight must beTrue
      Square(0, 3).isLight must beTrue
      Square(1, 0).isLight must beTrue
      Square(3, 0).isLight must beTrue
    }

    "accept algebraic arguments" in {
      Square(0, 0) must_== Square('a', 1)
      Square(7, 7) must_== Square('h', 8)

      Square('i', 1) must throwAn[IllegalArgumentException]
      Square('a', 9) must throwAn[IllegalArgumentException]
    }
  }
}