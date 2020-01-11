package khh;

public class KhacHang {
	private String tenKhachHang;
	private String diaChi;

	public KhacHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KhacHang(String tenKhachHang, String diaChi) {
		super();
		this.tenKhachHang = tenKhachHang;
		this.diaChi = diaChi;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public static void main(String[] args) {
		KhacHang kh= new KhacHang("toàn", "qn");
		System.out.println(kh.getTenKhachHang());
	}
}
