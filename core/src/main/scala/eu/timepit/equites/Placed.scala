// Equites, a Scala chess playground
// Copyright © 2013 Frank S. Thomas <frank@timepit.eu>
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

import scalaz._

trait PlacedInstances {
  implicit def placedEqual[A] = new Equal[Placed[A]] {
    def equal(p1: Placed[A], p2: Placed[A]): Boolean = p1 == p2
  }

  implicit object placedInstance extends Functor[Placed] {
    // Functor
    def map[A, B](fa: Placed[A])(f: A => B): Placed[B] = fa.map(f)
  }
}

object Placed extends PlacedInstances

case class Placed[+A](elem: A, square: Square) {
  def map[B](f: A => B): Placed[B] = copy(f(elem))
}
