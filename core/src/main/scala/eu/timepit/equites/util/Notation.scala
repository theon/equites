// Equites, a Scala chess playground
// Copyright © 2011, 2013 Frank S. Thomas <frank@timepit.eu>
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
package util

import scala.collection.immutable.NumericRange

object Notation {
  val algebraicFileRange: NumericRange[Char] =
    (Rules.fileRange.start + 'a').toChar to
    (Rules.fileRange.end   + 'a').toChar

  val algebraicRankRange: Range =
    (Rules.rankRange.start + 1) to
    (Rules.rankRange.end   + 1)

  val numericFileRange: Range =
    (Rules.fileRange.start + 1) to
    (Rules.fileRange.end   + 1)

  def numericRankRange: Range = algebraicRankRange

  def pieceFromLetter(c: Char): Option[Piece] = c match {
    case 'K' => Some(King(White))
    case 'Q' => Some(Queen(White))
    case 'R' => Some(Rook(White))
    case 'B' => Some(Bishop(White))
    case 'N' => Some(Knight(White))
    case 'P' => Some(Pawn(White))
    case 'k' => Some(King(Black))
    case 'q' => Some(Queen(Black))
    case 'r' => Some(Rook(Black))
    case 'b' => Some(Bishop(Black))
    case 'n' => Some(Knight(Black))
    case 'p' => Some(Pawn(Black))
    case _   => None
  }

  def pieceFromFigurine(c: Char): Option[Piece] = c match {
    case '\u2654' => Some(King(White))
    case '\u2655' => Some(Queen(White))
    case '\u2656' => Some(Rook(White))
    case '\u2657' => Some(Bishop(White))
    case '\u2658' => Some(Knight(White))
    case '\u2659' => Some(Pawn(White))
    case '\u265A' => Some(King(Black))
    case '\u265B' => Some(Queen(Black))
    case '\u265C' => Some(Rook(Black))
    case '\u265D' => Some(Bishop(Black))
    case '\u265E' => Some(Knight(Black))
    case '\u265F' => Some(Pawn(Black))
    case _ => None
  }

  def boardFromFen(placement: String): Board = {
    def expandDigits(target: String): String =
      """\d""".r.replaceAllIn(target, "1" * _.toString.toInt)

    val mapping = for {
      (rankStr, rank) <- placement.split("/").reverse.zipWithIndex
      (pieceChar, file) <- expandDigits(rankStr).zipWithIndex
      piece <- pieceFromLetter(pieceChar)
    } yield (Square(file, rank), piece)

    Board(mapping: _*)
  }
}
