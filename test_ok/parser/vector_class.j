class Main123 {
	Void main () {
		return null;
	}
}

class Vector {
	Int x;
	Int y;

	Void addVector(Vector that) {
		this.x = this.x + that.x;
		this.y = this.y + that.y;
	}

	Void scaleVector(Int x) {
		this.x = this.x * x;
		this.y = this.y * x;
	}

	Int dotVector(Vector that) {
		Int result;
		result = result + this.x * that.x;
		result = result + this.y * that.y;
		return result;
	}

	Bool isEqual_v1(Vector that) {
		if (that == null) {
			return false;
		} else {
			return this.x == that.x && this.y == that.y;
		}
	}

	Bool isEqual_v2(Vector that) {
		if (!that) {
			return false;
		} else {
			return this.x == that.x && this.y == that.y;
		}
	}

	Bool isEqual_v3(Vector that) {
		return !that && this.x == that.x && this.y == that.y;
	}
}
