// Equites, a simple chess interface
// Copyright © 2011 Frank S. Thomas <f.thomas@gmx.de>
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

package equites

sealed abstract class Piece(val color: Color) {
  def pieceType: PieceType

  def isFriendOf  (other: Piece): Boolean = color == other.color
  def isOpponentOf(other: Piece): Boolean = color != other.color
}

class King  (color: Color) extends Piece(color) { def pieceType = King   }
class Queen (color: Color) extends Piece(color) { def pieceType = Queen  }
class Rook  (color: Color) extends Piece(color) { def pieceType = Rook   }
class Bishop(color: Color) extends Piece(color) { def pieceType = Bishop }
class Knight(color: Color) extends Piece(color) { def pieceType = Knight }
class Pawn  (color: Color) extends Piece(color) { def pieceType = Pawn   }

sealed abstract class PieceType

trait PieceCompanion {
  def unapply(piece: Piece): Option[Color] = Some(piece.color)
}

object King   extends PieceType with PieceCompanion
object Queen  extends PieceType with PieceCompanion
object Rook   extends PieceType with PieceCompanion
object Bishop extends PieceType with PieceCompanion
object Knight extends PieceType with PieceCompanion
object Pawn   extends PieceType with PieceCompanion