import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class motel extends JFrame {

    private JPasswordField matkhauField;
    private JFrame login;
    private String url, user, pass;
    public static int vt, cot;
    private static String value, tong;
    private static JTextField  txthoten, txtsdt, txtquequan, txtsocccd, txtgiadien, txtgianuoc, txtgiathue, tenField, txtstdnd;
    private static JButton trangchu, bill, thongke;
    private static JPanel trangchu2, trangchuhoadon;

    class thongtin {
        private String phong, hoten, sdt, giathue;

        public thongtin(String phong, String hoten, String sdt, String giathue) {
            this.phong = phong;
            this.hoten = hoten;
            this.sdt = sdt;
            this.giathue = giathue;
        }

        public String getPhong() {
            return phong;
        }

        public void setPhong(String phong) {
            this.phong = phong;
        }

        public String getHoten() {
            return hoten;
        }

        public void setHoten(String hoten) {
            this.hoten = hoten;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }

        public String getGiathue() {
            return giathue;
        }

        public void setGiathue(String giathue) {
            this.giathue = giathue;
        }

    }





//PHẦN ĐĂNG NHẬP
    public motel() {
        // Tạo JFrame
        login = new JFrame();
        login.setTitle("Đăng nhập");
        login.setSize(400, 270);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.setResizable(false);
        login.getContentPane().setBackground(Color.pink);
        login.setLayout(null);
        //thêm icon
        ImageIcon icon = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/nguoidung.png");
        login.setIconImage(icon.getImage());

        JLabel tieudedangnhap = new JLabel("Đăng nhập");
        tieudedangnhap.setFont(new Font("Arial", Font.BOLD, 22));
        tieudedangnhap.setBounds(140, 10, 150, 40);
        login.add(tieudedangnhap);

        JLabel ten = new JLabel("Tên đăng nhập:");
        ten.setFont(new Font("Arial", Font.BOLD, 14));
        ten.setBounds(5, 65, 200, 20);
        login.add(ten);

        tenField = new JTextField();
        tenField.setBounds(120, 60, 250, 35);
        tenField.setFont(new Font("Arial", Font.BOLD, 14));
        login.add(tenField);

        JLabel matkhau = new JLabel("Mật khẩu:");
        matkhau.setFont(new Font("Arial", Font.BOLD, 14));
        matkhau.setBounds(45, 115, 200, 20);
        login.add(matkhau);

        matkhauField = new JPasswordField();
        matkhauField.setBounds(120, 110, 250, 35);
        matkhauField.setFont(new Font("Arial", Font.BOLD, 14));
        login.add(matkhauField);

        JCheckBox hienmatkhau = new JCheckBox("Hiển thị mật khẩu");
        hienmatkhau.setFont(new Font("Arial", Font.BOLD, 10));
        hienmatkhau.setBounds(117, 150, 123, 14);
        hienmatkhau.setBackground(Color.pink);

        login.add(hienmatkhau);
        hienmatkhau.addActionListener(e -> {
            if (hienmatkhau.isSelected()) {
                matkhauField.setEchoChar((char) 0); // Hiển thị mật khẩu
            } else {
                matkhauField.setEchoChar('•'); // Ẩn mật khẩu
            }
        });

        JButton dangnhap = new JButton("Đăng nhập");
        dangnhap.setFont(new Font("Arial", Font.BOLD, 16));
        dangnhap.setBounds(65, 180, 120, 30);
        login.add(dangnhap);
        dangnhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trangchu.doClick();
            }
        });

        JButton dangki = new JButton("Đăng kí");
        dangki.setFont(new Font("Arial", Font.BOLD, 16));
        dangki.setBounds(205, 180, 120, 30);
        login.add(dangki);

        dangnhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = tenField.getText();
                String password = new String(matkhauField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (checkLogin(username, password)) {
                    login.setVisible(false);
                    new manhinhchinh();

                } else {
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dangki.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login.setVisible(false);
                JFrame dangkiframe = new JFrame();
                dangkiframe.setTitle("Đăng kí");
                dangkiframe.setSize(400, 400);
                dangkiframe.setLocationRelativeTo(null);
                dangkiframe.setLayout(null);
                dangkiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dangkiframe.getContentPane().setBackground(Color.pink);
                dangkiframe.setResizable(false);

                // Giao diện
                JLabel dangkitaikhoan = new JLabel("Nhập thông tin");
                dangkitaikhoan.setFont(new Font("Arial", Font.BOLD, 20));
                dangkitaikhoan.setBounds(130, 5, 150, 40);
                dangkiframe.add(dangkitaikhoan);

                JLabel tennguoidung = new JLabel("Họ và tên");
                tennguoidung.setFont(new Font("Arial", Font.BOLD, 14));
                tennguoidung.setBounds(10, 40, 150, 40);
                dangkiframe.add(tennguoidung);

                JTextField txttennguoidung = new JTextField();
                txttennguoidung.setFont(new Font("Arial", Font.PLAIN, 14));
                txttennguoidung.setBounds(10, 70, 370, 27);
                dangkiframe.add(txttennguoidung);

                JLabel tendn = new JLabel("Tên đăng nhập");
                tendn.setFont(new Font("Arial", Font.BOLD, 14));
                tendn.setBounds(10, 90, 150, 40);
                dangkiframe.add(tendn);

                JTextField txttendn = new JTextField();
                txttendn.setFont(new Font("Arial", Font.PLAIN, 14));
                txttendn.setBounds(10, 120, 370, 27);
                dangkiframe.add(txttendn);

                JLabel std = new JLabel("Số điện thoại");
                std.setFont(new Font("Arial", Font.BOLD, 14));
                std.setBounds(10, 140, 150, 40);
                dangkiframe.add(std);

                txtstdnd = new JTextField();
                txtstdnd.setFont(new Font("Arial", Font.PLAIN, 14));
                txtstdnd.setBounds(10, 170, 370, 27);
                dangkiframe.add(txtstdnd);

                JLabel matkhau = new JLabel("Mật khẩu");
                matkhau.setFont(new Font("Arial", Font.BOLD, 14));
                matkhau.setBounds(10, 190, 150, 40);
                dangkiframe.add(matkhau);

                JTextField txtmatkhau = new JTextField();
                txtmatkhau.setFont(new Font("Arial", Font.PLAIN, 14));
                txtmatkhau.setBounds(10, 220, 370, 27);
                dangkiframe.add(txtmatkhau);

                JButton dangkitk = new JButton("Đăng kí tài khoản");
                dangkitk.setFont(new Font("Arial", Font.BOLD, 14));
                dangkitk.setBounds(110, 270, 170, 30);
                dangkiframe.add(dangkitk);

                ImageIcon iconquaylai = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/dangxuat.png");
                Image ktquaylai = iconquaylai.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
                ImageIcon ganvaobuttondangxuat = new ImageIcon(ktquaylai);

                JButton quaylai  = new JButton(ganvaobuttondangxuat);
                quaylai.setBounds(290, 270, 27, 27);
                dangkiframe.add(quaylai);

                quaylai.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dangkiframe.setVisible(false);
                         new motel();
                    }
                });


                dangkiframe.setVisible(true);

                dangkitk.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String tnd = txttennguoidung.getText().trim();
                        String tdn = txttendn.getText().trim();
                        String sdtnd = txtstdnd.getText().trim();
                        String mknd = txtmatkhau.getText().trim();

                        if (tnd.isEmpty() || sdtnd.isEmpty() || mknd.isEmpty() || tdn.isEmpty()) {
                            JOptionPane.showMessageDialog(dangkiframe, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (!sdtnd.matches("\\d{10,11}")) {
                            JOptionPane.showMessageDialog(dangkiframe, "Số điện thoại không hơp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;}

                        String ur = "jdbc:mysql://127.0.0.1:3306/login";
                        String us = "root";
                        String pas = "1412006";

                        String sql = "INSERT INTO login.user (fullname, username, password, sdt) VALUES (?, ?, ?, ?)";

                        try (Connection conn = DriverManager.getConnection(ur, us, pas);
                             PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, tnd);
                            stmt.setString(2, tdn);
                            stmt.setString(3, mknd);
                            stmt.setString(4, sdtnd);

                            int rowsAffected = stmt.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(dangkiframe, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(dangkiframe, "Đăng ký thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                            dangkiframe.setVisible(false);
                            login.setVisible(true);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(dangkiframe, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        });



        login.setVisible(true);
    }
    private boolean checkLogin(String username, String password) {

        url = "jdbc:mysql://127.0.0.1:3306/login";
        user = "root";
        pass = "1412006";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM login.user WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu có kết quả
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    public Connection getConnection() {
        String u = "jdbc:mysql://127.0.0.1:3306/login";
        String r = "root";
        String pa = "1412006";
        try {
            return DriverManager.getConnection(u, r, pa);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kết nối cơ sở dữ liệu thất bại!");
            return null;
        }
    }




    //SAU KHI ĐĂNG NHẬP
    public class manhinhchinh extends JFrame {
        private DefaultTableModel tablemodel;
        private ArrayList<thongtin> khach = new ArrayList();
        private DefaultTableModel bangmodel;


        private JComboBox cbbphong, ngay, thang, nam, hdphong, tkphong;
        private static JTable bang;

        public manhinhchinh() {

            JFrame frame = new JFrame();
            frame.setTitle("Quản lí nhà trọ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.getContentPane().setBackground(Color.LIGHT_GRAY);

            ImageIcon iconngoinha = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/iconngoinha.png");
            Image kticon = iconngoinha.getImage().getScaledInstance(512, 512, Image.SCALE_SMOOTH);

            // Gán icon cho JFrame
            frame.setIconImage(kticon);


//Tên QUẢN LÍ NHÀ TRỌ
            JLabel quanlinhatro = new JLabel("QUẢN LÍ NHÀ TRỌ");
            quanlinhatro.setFont(new Font("Arial", Font.BOLD, 14));
            quanlinhatro.setBounds(20, 20, 200, 20);
            frame.add(quanlinhatro);
//Hiện thị xin chào
            JLabel xinchao = new JLabel("Xin chào,");
            xinchao.setFont(new Font("Arial", Font.BOLD, 14));
            xinchao.setBounds(50, 40, 200, 20);
            frame.add(xinchao);
//Tên người dùng
            String u = "jdbc:mysql://127.0.0.1:3306/login";
            String r = "root";
            String pa = "1412006";
            String layten = tenField.getText();
            String sql = "SELECT fullname FROM user WHERE username = ?";

            try (Connection conn = DriverManager.getConnection(u, r, pa)) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, layten);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            String nd = rs.getString("fullname");

                            int panelWidth = 160;
                            int panelHeight = 100;
                            int labelWidth = 200;
                            int labelHeight = 20;
                            int x = (panelWidth - labelWidth) / 2;
                            int y = (panelHeight - labelHeight) / 2 + 20;
                            JLabel tennguoidung = new JLabel(nd, SwingConstants.CENTER);
                            tennguoidung.setFont(new Font("Arial", Font.BOLD, 14));
                            tennguoidung.setBounds(x, y, labelWidth, labelHeight);
                            frame.add(tennguoidung);

                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }


//Tạo đường kẻ ngang
            JSeparator kengangduongquanlitro = new JSeparator(SwingConstants.HORIZONTAL);
            kengangduongquanlitro.setForeground(Color.BLACK);
            kengangduongquanlitro.setBounds(0, 100, 160, 20);
            frame.add(kengangduongquanlitro, BorderLayout.CENTER);
//Tạo đường kẻ dọc (Quản lí nhà trọ)
            JSeparator keduongquanlitro = new JSeparator(SwingConstants.VERTICAL);
            keduongquanlitro.setForeground(Color.BLACK);
            keduongquanlitro.setBounds(160, 0, 20, 465);
            frame.add(keduongquanlitro, BorderLayout.CENTER);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//Tạo JButton Trang chủ
            trangchu = new JButton("Trang chủ");
            trangchu.setFont(new Font("Arial", Font.BOLD, 16));
            trangchu.setBounds(0, 120, 161, 40);
            trangchu.setFocusPainted(false);
            frame.add(trangchu);
            trangchu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
//Panel hiện thị chính TRANG CHỦ
                    if (motel.trangchuhoadon != null && motel.trangchuhoadon.isVisible()) {
                        trangchuhoadon.setVisible(false);
                    }


                    trangchu2 = new JPanel();
                    trangchu2.setLayout(null);
                    trangchu2.setBounds(161, 0, 625, 462);
                    trangchu2.setBackground(new Color(140, 210, 47));
                    frame.add(trangchu2);
//Hiển thị trong panel trang chủ
                    JLabel tieude = new JLabel("Trang chủ");
                    tieude.setBounds(5, 5, 100, 20);
                    tieude.setFont(new Font("Arial", Font.BOLD, 16));
                    trangchu2.add(tieude);
//Tạo kẻ ngang thanh tác vụ trên cho tiêu đề
                    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                    separator.setBounds(0, 30, 800, 200);
                    separator.setForeground(Color.BLACK);
                    trangchu2.add(separator, BorderLayout.CENTER);
//Tạo kẻ dọc cho trang chủ tiêu đề
                    JSeparator kedocnho = new JSeparator(SwingConstants.VERTICAL);
                    kedocnho.setForeground(Color.BLACK);
                    kedocnho.setBounds(90, 0, 20, 30);
                    trangchu2.add(kedocnho, BorderLayout.CENTER);

//Phần tìm kiếm
                    JLabel timkiem = new JLabel("Tìm kiếm:");
                    timkiem.setFont(new Font("Arial", Font.BOLD, 14));
                    timkiem.setBounds(280, 5, 200, 20);
                    trangchu2.add(timkiem);

                    JTextField timkiem1 = new JTextField();
                    timkiem1.setBounds(352, 5, 190, 20);
                    trangchu2.add(timkiem1);

                    // Tạo icon kính lúp với kích thước phù hợp với JButton
                    ImageIcon icontimkiem = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/kinhlup1.png");
                    Image chinhkichthuoc1 = icontimkiem.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
                    ImageIcon ganvaoicontimkiem = new ImageIcon(chinhkichthuoc1);

                    JButton tim = new JButton(ganvaoicontimkiem);
                    tim.setBounds(550, 3, 25, 25);
                    trangchu2.add(tim);

                    tim.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String keyword = timkiem1.getText().trim(); // Xóa khoảng trắng thừa
                            if (keyword.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa để tìm kiếm!");
                                return;
                            }

                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "1412006")) {
                                String sql = "SELECT phongdachon, hovaten, sodienthoai, tienthue FROM login.thongtinkhach WHERE phongdachon LIKE ? OR hovaten LIKE ?" +
                                        " OR thuengay LIKE ? OR socccd LIKE ? OR sodienthoai LIKE ? OR quequano LIKE ? OR tiendien LIKE ? OR tiennuoc LIKE ? OR tienthue LIKE ?";
                                PreparedStatement pst = conn.prepareStatement(sql);
                                pst.setString(1, "%" + keyword + "%");
                                pst.setString(2, "%" + keyword + "%");
                                pst.setString(3, "%" + keyword + "%");
                                pst.setString(4, "%" + keyword + "%");
                                pst.setString(5, "%" + keyword + "%");
                                pst.setString(6, "%" + keyword + "%");
                                pst.setString(7, "%" + keyword + "%");
                                pst.setString(8, "%" + keyword + "%");
                                pst.setString(9, "%" + keyword + "%");


                                ResultSet rs = pst.executeQuery();
                                bangmodel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

                                if (!rs.isBeforeFirst()) { // Kiểm tra nếu không có kết quả
                                    JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả phù hợp!");
                                } else {
                                    while (rs.next()) {
                                        String room = rs.getString("phongdachon");
                                        String name = rs.getString("hovaten");
                                        String phone = rs.getString("sodienthoai");
                                        String rental = rs.getString("tienthue");

                                        bangmodel.addRow(new Object[]{room, name, phone, rental, "Chi tiết"});
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu!");
                            }
                        }
                    });


//Button đăng xuất
                    ImageIcon icondangxuat = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/dangxuat.png");
                    Image chinhkichthuoc2 = icondangxuat.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
                    ImageIcon ganvaobuttondangxuat = new ImageIcon(chinhkichthuoc2);

                    JButton dangxuat  = new JButton(ganvaobuttondangxuat);
                    dangxuat.setBounds(595, 2, 27, 27);
                    trangchu2.add(dangxuat);

                    dangxuat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            frame.setVisible(false);
                            new motel();
                        }
                    });

//TẠO PANEL CON TRONG PANEL CHÍNH
                    JPanel trangchucon = new JPanel();
                    trangchucon.setLayout(null);
                    trangchucon.setBackground(Color.PINK);//new Color(140, 210, 47)
                    trangchucon.setBounds(1, 31, 625, 431);
                    trangchu2.add(trangchucon);
//Tạo panel hiện thị bảng------<
                    JPanel chuabang = new JPanel();
                    chuabang.setBackground(Color.WHITE);
                    chuabang.setBounds(10, 10, 365, 410);
                    trangchucon.add(chuabang);
//Tạo bảng hiện thị
                    bang = new JTable();
                    bangmodel = new DefaultTableModel(new Object[]{"Phòng", "Họ tên", "SĐT", "Giá thuê", "Chi tiết"}, 0);
                    bang.setModel(bangmodel);
                    bang.setFont(new Font("Arial", Font.BOLD, 11));
                    bang.setPreferredScrollableViewportSize(new java.awt.Dimension(359, 380));
                    bang.setFillsViewportHeight(true);
                    chuabang.add(new JScrollPane(bang), BorderLayout.CENTER);

//Thêm dữ liệu vào bảng khi đăng nhập
                    hien();


//Tạo panel nhâập thông tin
                    JPanel nhapthongtin = new JPanel();
                    nhapthongtin.setLayout(null);
                    nhapthongtin.setBackground(Color.WHITE);
                    nhapthongtin.setBounds(385, 10, 230, 410);
                    trangchucon.add(nhapthongtin);
                    nhapthongtin.setBorder(BorderFactory.createTitledBorder("Nhập thông tin"));
                    JLabel chonphong = new JLabel("Chọn phòng: ");
                    chonphong.setFont(new Font("Arial", Font.BOLD, 14));
                    chonphong.setBounds(10, 20, 200, 20);
                    nhapthongtin.add(chonphong);
                    String[] listphong = {"Phòng 1", "Phòng 2", "Phòng 3", "Phòng 4", "Phòng 5", "Phòng 6", "Phòng 7", "Phòng 8", "Phòng 9","Phòng 10",
                                          "Phòng 11", "Phòng 12", "Phòng 13", "Phòng 14", "Phòng 15", "Phòng 16", "Phòng 17", "Phòng 18","Phòng 19","Phòng 20",
                                          "Phòng 21", "Phòng 22", "Phòng 23", "Phòng 24", "Phòng 25", "Phòng 26", "Phòng 27", "Phòng 28", "Phòng 29","Phòng 30",
                                          };
                    cbbphong = new JComboBox(listphong);
                    cbbphong.setBounds(115, 22, 80, 20);
                    nhapthongtin.add(cbbphong);
                    JLabel hoten = new JLabel("Họ và tên: ");
                    hoten.setFont(new Font("Arial", Font.BOLD, 14));
                    hoten.setBounds(10, 50, 200, 20);
                    nhapthongtin.add(hoten);
                    txthoten = new JTextField();
                    txthoten.setBounds(90, 50, 130, 20);
                    nhapthongtin.add(txthoten);


                    JLabel sdt = new JLabel("SĐT: ");
                    sdt.setFont(new Font("Arial", Font.BOLD, 14));
                    sdt.setBounds(10, 80, 200, 20);
                    nhapthongtin.add(sdt);
                    txtsdt = new JTextField();
                    txtsdt.setBounds(90, 80, 130, 20);
                    nhapthongtin.add(txtsdt);

                    JLabel quequan = new JLabel("Quê quán: ");
                    quequan.setFont(new Font("Arial", Font.BOLD, 14));
                    quequan.setBounds(10, 110, 200, 20);
                    nhapthongtin.add(quequan);
                    txtquequan = new JTextField();
                    txtquequan.setBounds(90, 110, 130, 20);
                    nhapthongtin.add(txtquequan);

                    JLabel cccd = new JLabel("Số CCCD: ");
                    cccd.setFont(new Font("Arial", Font.BOLD, 14));
                    cccd.setBounds(10, 140, 200, 20);
                    nhapthongtin.add(cccd);
                    txtsocccd = new JTextField();
                    txtsocccd.setBounds(90, 140, 130, 20);
                    nhapthongtin.add(txtsocccd);
//Cập nhật ngày
                    JLabel ngaythue = new JLabel("Ngày thuê: ");
                    ngaythue.setFont(new Font("Arial", Font.BOLD, 14));
                    ngaythue.setBounds(10, 170, 200, 20);
                    nhapthongtin.add(ngaythue);
                    String[] nam1 = {"2030", "2029", "2028", "2027", "2026", "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015"};
                    nam = new JComboBox(nam1);
                    nam.setBounds(90, 170, 55, 20);
                    nhapthongtin.add(nam);
                    String[] thang1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                    thang = new JComboBox(thang1);
                    thang.setBounds(145, 170, 40, 20);
                    nhapthongtin.add(thang);
                    ngay = new JComboBox();
                    ngay.setBounds(185, 170, 40, 20);
                    nhapthongtin.add(ngay);

                    ActionListener capNhatNgay = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int month = Integer.parseInt((String) thang.getSelectedItem());
                            int year = Integer.parseInt((String) nam.getSelectedItem());
                            int songay = 0;
                            boolean xdnam = false;
                            if ((year % 4 == 0 && year % 100 != 00) || (year % 100 == 0 && year % 400 == 0)) {
                                xdnam = true;
                            } else if (year % 100 == 0 && year % 400 != 0) {
                                xdnam = false;
                            }
                            switch (month) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                case 8:
                                case 10:
                                case 12:
                                    songay = 31;
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    songay = 30;
                                    break;
                                case 2:
                                    if (xdnam) {
                                        songay = 29;
                                    } else {
                                        songay = 28;
                                    }
                            }

                            ngay.removeAllItems();
                            for (int i = 1; i <= songay; i++) {
                                ngay.addItem(String.valueOf(i));
                            }
                        }
                    };

                    thang.addActionListener(capNhatNgay);
                    nam.addActionListener(capNhatNgay);
                    capNhatNgay.actionPerformed(null);

                    JLabel giadien = new JLabel("Giá điện (đ/kWh): ");
                    giadien.setFont(new Font("Arial", Font.BOLD, 14));
                    giadien.setBounds(10, 200, 200, 20);
                    nhapthongtin.add(giadien);
                    txtgiadien = new JTextField();
                    txtgiadien.setBounds(145, 200, 75, 20);
                    nhapthongtin.add(txtgiadien);


                    JLabel gianuoc = new JLabel("Giá nước (đ/khối): ");
                    gianuoc.setFont(new Font("Arial", Font.BOLD, 14));
                    gianuoc.setBounds(10, 230, 200, 20);
                    nhapthongtin.add(gianuoc);
                    txtgianuoc = new JTextField();
                    txtgianuoc.setBounds(145, 230, 75, 20);
                    nhapthongtin.add(txtgianuoc);

                    JLabel giathue = new JLabel("Giá thuê (đ/tháng): ");
                    giathue.setFont(new Font("Arial", Font.BOLD, 14));
                    giathue.setBounds(10, 260, 200, 20);
                    nhapthongtin.add(giathue);
                    txtgiathue = new JTextField();
                    txtgiathue.setBounds(145, 260, 75, 20);
                    nhapthongtin.add(txtgiathue);

                    //Chức năng thêm
                    JButton themkhach = new JButton("Thêm");
                    themkhach.setBounds(12, 340, 70, 20);
                    nhapthongtin.add(themkhach);



                    themkhach.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Lấy dữ liệu từ các trường nhập liệu
                            String phongdachon = (String) cbbphong.getSelectedItem();
                            String hovaten = txthoten.getText();
                            String sodienthoai = txtsdt.getText();
                            String quequano = txtquequan.getText();
                            String socccd = txtsocccd.getText();
                            String thuengay = (String) nam.getSelectedItem() + "-" + (String) thang.getSelectedItem() + "-" + (String) ngay.getSelectedItem();
                            String tiendien = txtgiadien.getText();
                            String tiennuoc = txtgianuoc.getText();
                            String tienthue = txtgiathue.getText();

                            // Kiểm tra dữ liệu nhập vào
                            if (hovaten.isEmpty() || sodienthoai.isEmpty() || tienthue.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            if (!sodienthoai.matches("\\d{10,11}")) {
                                JOptionPane.showMessageDialog(nhapthongtin, "Số điện thoại không hơp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;}
                            if (!socccd.matches("\\d{12}")) {
                                JOptionPane.showMessageDialog(nhapthongtin, "Số CCCD không hơp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;}

                            // Kiểm tra trùng lặp số phòng trong cơ sở dữ liệu
                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "1412006")) {
                                // Câu lệnh kiểm tra xem phòng đã tồn tại trong cơ sở dữ liệu chưa
                                String checkSql = "SELECT COUNT(*) FROM thongtinkhach WHERE phongdachon = ?";
                                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                                    checkStmt.setString(1, phongdachon);
                                    ResultSet rs = checkStmt.executeQuery();

                                    // Kiểm tra kết quả truy vấn
                                    if (rs.next() && rs.getInt(1) > 0) {
                                        JOptionPane.showMessageDialog(null, "Phòng đã có người thuê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        return; // Nếu phòng đã tồn tại, không thực hiện thêm
                                    }
                                }

                                // Tạo đối tượng thongtin mới và thêm vào danh sách khách
                                thongtin newKhach = new thongtin(phongdachon, hovaten, sodienthoai, tienthue);
                                khach.add(newKhach);

                                // Cập nhật bảng hiển thị thông tin khách
                                bangmodel.addRow(new Object[]{phongdachon, hovaten, sodienthoai, tienthue, "Chi tiết"});

                                // Câu lệnh SQL INSERT vào cơ sở dữ liệu
                                String sql = "INSERT INTO thongtinkhach (phongdachon, hovaten, sodienthoai, quequano, socccd, thuengay, tiendien, tiennuoc, tienthue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                    stmt.setString(1, phongdachon);
                                    stmt.setString(2, hovaten);
                                    stmt.setString(3, sodienthoai);
                                    stmt.setString(4, quequano);
                                    stmt.setString(5, socccd);
                                    stmt.setString(6, thuengay);
                                    stmt.setString(7, tiendien);
                                    stmt.setString(8, tiennuoc);
                                    stmt.setString(9, tienthue);

                                    // Thực thi câu lệnh SQL
                                    int rowsAffected = stmt.executeUpdate();
                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Lỗi khi thêm khách vào cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    }
                                    txthoten.setText("");
                                    txtsdt.setText("");
                                    txtquequan.setText("");
                                    txtsocccd.setText("");
                                    txtgiadien.setText("");
                                    txtgianuoc.setText("");
                                    txtgiathue.setText("");

                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });



                    JButton xoa = new JButton("Xóa");
                    xoa.setBounds(79, 340, 70, 20);
                    nhapthongtin.add(xoa);

                    xoa.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String idphong =  (String) cbbphong.getSelectedItem();
                            deleteRecord(idphong);
                            bangmodel.setRowCount(0);
                            hien();

                        }
                    });

                    JButton datlai = new JButton("Đặt lại");
                    datlai.setBounds(147, 340, 70, 20);
                    nhapthongtin.add(datlai);

                    datlai.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            txthoten.setText("");
                            txtsdt.setText("");
                            txtquequan.setText("");
                            txtsocccd.setText("");
                            txtgiadien.setText("");
                            txtgianuoc.setText("");
                            txtgiathue.setText("");
                            bangmodel.setRowCount(0);
                            hien();
                        }
                    });


                    bang.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            vt = bang.rowAtPoint(e.getPoint());
                            cot = bang.columnAtPoint(e.getPoint());
                            String httextField = (String) bang.getValueAt(vt, 0);
                            String batdau = httextField;

                            if (cot < 4) {
                                String urll = "jdbc:mysql://127.0.0.1:3306/login";
                                String userr = "root";
                                String passwordd = "1412006";

                                String sql = "SELECT phongdachon, hovaten, sodienthoai, quequano, socccd, thuengay, tiendien, tiennuoc, tienthue FROM thongtinkhach WHERE phongdachon = ?";

                                try (Connection conn = DriverManager.getConnection(urll, userr, passwordd);
                                     PreparedStatement ps = conn.prepareStatement(sql)) {
                                    ps.setString(1, batdau);

                                    try (ResultSet rs = ps.executeQuery()) {
                                        if (rs.next()) {
                                            // Lấy dữ liệu từ các cột của bảng trong cơ sở dữ liệu
                                            String pphong = rs.getString("phongdachon");
                                            String tten = rs.getString("hovaten");
                                            String ssdt = rs.getString("sodienthoai");
                                            String qquequan = rs.getString("quequano");
                                            String ssocccd = rs.getString("socccd");
                                            String tthuengay = rs.getString("thuengay");
                                            String ttiendien = rs.getString("tiendien");
                                            String ttiennuoc = rs.getString("tiennuoc");
                                            String ttienthue = rs.getString("tienthue");

                                            // Hiển thị lại trên textField
                                            txthoten.setText(tten);
                                            txtsdt.setText(ssdt);
                                            txtquequan.setText(qquequan);
                                            txtsocccd.setText(ssocccd);
                                            txtgiadien.setText(ttiendien);
                                            txtgianuoc.setText(ttiennuoc);
                                            txtgiathue.setText(ttienthue);

                                            cbbphong.setSelectedItem(pphong);

                                            if (tthuengay != null) {
                                                String formattedDate = tthuengay;
                                                // Tách năm, tháng, ngày
                                                String[] parts = formattedDate.split("-");
                                                String year = parts[0];
                                                String month = parts[1];
                                                String day = parts[2];

                                                // Loại bỏ số 0 ở đầu
                                                String boday = String.valueOf(Integer.parseInt(day));
                                                String bomonth = String.valueOf(Integer.parseInt(month));

                                                // Đảm bảo các JComboBox đã được khởi tạo và chứa giá trị
                                                if (nam.getItemCount() > 0 && thang.getItemCount() > 0 && ngay.getItemCount() > 0) {
                                                    // Cập nhật JComboBox với giá trị đã lấy
                                                    nam.setSelectedItem(year);
                                                    thang.setSelectedItem(bomonth);
                                                    ngay.setSelectedItem(boday);
                                                }
                                            }

                                        }
                                    }

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });

                    //CẬP NHẬT
                    JButton capnhat = new JButton("Cập nhật");
                    capnhat.setBounds(70, 365, 85, 20);
                    nhapthongtin.add(capnhat);
                    capnhat.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Lấy dữ liệu từ giao diện
                            String phong = (String) cbbphong.getSelectedItem();
                            String hoten = txthoten.getText();
                            String dienthoai = txtsdt.getText();
                            String que = txtquequan.getText();
                            String cccd = txtsocccd.getText();
                            String ngaythue = (String) nam.getSelectedItem() + "-" + (String) thang.getSelectedItem() + "-" + (String) ngay.getSelectedItem();
                            String dien = txtgiadien.getText();
                            String nuoc = txtgianuoc.getText();
                            String gia = txtgiathue.getText();

                            // Câu lệnh SQL
                            String sql = "UPDATE login.thongtinkhach SET phongdachon = ?, hovaten = ?, sodienthoai = ?, quequano = ?, socccd = ?, thuengay = ?, tiendien = ?, tiennuoc = ?, tienthue = ? WHERE phongdachon = ?";

                            // Kết nối cơ sở dữ liệu
                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "1412006");
                                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                                // Thiết lập tham số cho câu lệnh SQL
                                stmt.setString(1, phong);
                                stmt.setString(2, hoten);
                                stmt.setString(3, dienthoai);
                                stmt.setString(4, que);
                                stmt.setString(5, cccd);
                                stmt.setString(6, ngaythue);
                                stmt.setString(7, dien);
                                stmt.setString(8, nuoc);
                                stmt.setString(9, gia);
                                stmt.setString(10, phong);
                                // Thực thi câu lệnh
                                int rowsAffected = stmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Khách đã được cập nhật!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Không tìm thấy khách với phòng này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                            bangmodel.setRowCount(0);
                            hien();
                        }
                    });
                    trangchu2.revalidate();
                    trangchu2.repaint();
                    trangchu2.setVisible(true);
                }
            });

//PHẦN HÓA ĐƠN
            bill = new JButton("Hóa đơn");
            bill.setFont(new Font("Arial", Font.BOLD, 16));
            bill.setBounds(0, 160, 160, 40);
            bill.setFocusPainted(false);
            frame.add(bill);

            bill.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (motel.trangchu2 != null && motel.trangchu2.isVisible()) {
                        trangchu2.setVisible(false);
                    }



                    trangchuhoadon = new JPanel();
                    trangchuhoadon.setLayout(null);
                    trangchuhoadon.setBounds(161, 0, 625, 462);
                    trangchuhoadon.setBackground(new Color(140, 210, 47));
                    frame.add(trangchuhoadon);

                    JLabel tieude = new JLabel("Hóa đơn");
                    tieude.setBounds(5, 5, 100, 20);
                    tieude.setFont(new Font("Arial", Font.BOLD, 16));
                    trangchuhoadon.add(tieude);

                    JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                    separator.setBounds(0, 30, 800, 200);
                    separator.setForeground(Color.BLACK);
                    trangchuhoadon.add(separator, BorderLayout.CENTER);

                    JSeparator kedocnho = new JSeparator(SwingConstants.VERTICAL);
                    kedocnho.setForeground(Color.BLACK);
                    kedocnho.setBounds(90, 0, 20, 30);
                    trangchuhoadon.add(kedocnho, BorderLayout.CENTER);

                    // JLabel cho ô tìm kiếm
                    JLabel timkiem = new JLabel("Tìm kiếm:");
                    timkiem.setFont(new Font("Arial", Font.BOLD, 14));
                    timkiem.setBounds(280, 5, 200, 20);
                    trangchuhoadon.add(timkiem);

                    JTextField timkiem11 = new JTextField();
                    timkiem11.setBounds(352, 5, 190, 20);
                    trangchuhoadon.add(timkiem11);

                    ImageIcon icontimkiem = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/kinhlup1.png");
                    Image chinhkichthuoc1 = icontimkiem.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
                    ImageIcon ganvaoicontimkiem = new ImageIcon(chinhkichthuoc1);

                    JButton tim = new JButton(ganvaoicontimkiem);
                    tim.setBounds(550, 3, 25, 25);
                    trangchuhoadon.add(tim);

// Xử lý sự kiện khi nhấn nút tìm kiếm
                    tim.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String keyword = timkiem11.getText().trim();
                            if (keyword.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa để tìm kiếm!");
                                return;
                            }

                            try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "1412006")) {
                                String sql = "SELECT phong, sodiencu, sonuoccu, sodienmoi, sonuocmoi, tongtienphong, ngaycu, ngaymoi, trangthai FROM hoadon WHERE phong LIKE ? OR sodiencu LIKE ? OR sonuoccu LIKE ? OR sodienmoi LIKE ? OR sonuocmoi LIKE ? OR tongtienphong LIKE ? OR ngaycu LIKE ? OR ngaymoi LIKE ? OR trangthai LIKE ?";
                                PreparedStatement pst = conn.prepareStatement(sql);

                                // Đặt giá trị cho các tham số trong truy vấn
                                for (int i = 1; i <= 9; i++) {
                                    pst.setString(i, "%" + keyword + "%");
                                }

                                ResultSet rs = pst.executeQuery();
                                tablemodel.setRowCount(0);

                                if (!rs.isBeforeFirst()) { // Kiểm tra nếu không có kết quả
                                    JOptionPane.showMessageDialog(null, "Không tìm thấy kết quả phù hợp!");
                                } else {
                                    while (rs.next()) {
                                        String p = rs.getString("phong");
                                        String sdc = rs.getString("sodiencu");
                                        String snc = rs.getString("sonuoccu");
                                        String sdm = rs.getString("sodienmoi");
                                        String snm = rs.getString("sonuocmoi");
                                        String ttp = rs.getString("tongtienphong");
                                        String nc = rs.getString("ngaycu");
                                        String nm = rs.getString("ngaymoi");
                                        String tth = rs.getString("trangthai");

                                        tablemodel.addRow(new Object[]{p, sdc, snc, sdm, snm, ttp, nc, nm, tth});
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu!");
                            }
                        }
                    });



                    ImageIcon icondangxuat = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/dangxuat.png");
                    Image chinhkichthuoc2 = icondangxuat.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
                    ImageIcon ganvaobuttondangxuat = new ImageIcon(chinhkichthuoc2);

                    JButton dangxuat  = new JButton(ganvaobuttondangxuat);
                    dangxuat.setBounds(595, 2, 27, 27);
                    trangchuhoadon.add(dangxuat);

                        JPanel hoadoncon = new JPanel();
                    hoadoncon.setLayout(null);
                    hoadoncon.setBackground(Color.PINK);//new Color(140, 210, 47)
                    hoadoncon.setBounds(1, 31, 625, 431);
                    trangchuhoadon.add(hoadoncon);

                    JPanel hienthihoadon = new JPanel();
                    hienthihoadon.setLayout(null);
                    hienthihoadon.setBackground(Color.WHITE);
                    hienthihoadon.setBounds(10, 10, 605, 411);
                    hoadoncon.add(hienthihoadon);

                    JTable table = new JTable();
                    tablemodel = new DefaultTableModel(
                            new Object[]{"STT","Phòng", "Số điện cũ", "Số điện mới", "Số nước cũ", "Số nước mới", "Tổng tiền", "Ngày cũ", "Ngày mới", "Trạng thái"}, 0
                    ) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            // Chỉ cho phép chỉnh sửa các cột từ 1 đến 8
                            return column >= 1 && column <= 8;
                        }
                    };
                    table.setModel(tablemodel);
                    table.setFont(new Font("Arial", Font.BOLD, 11));
                    table.setPreferredScrollableViewportSize(new java.awt.Dimension(359, 380));
                    table.setFillsViewportHeight(true);

                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setBounds(0, 0, 605, 410);
                    hienthihoadon.add(scrollPane);


                    hienhoadon();
                    trangchuhoadon.setVisible(true);
                }
            });




            frame.setVisible(true);


        }







        //////////////////////////////////////////////////////////////////////////
        //PHẦN BỔ SUNG
        private  void hien(){
            bang.getColumn("Chi tiết").setCellRenderer(new ButtonRenderer());
            bang.getColumn("Chi tiết").setCellEditor(new ButtonEditor(new JCheckBox()));

            String u = "jdbc:mysql://127.0.0.1:3306/login";
                String r = "root";
                String pa = "1412006";


                try (Connection conn = DriverManager.getConnection(u, r, pa)) {
                khach.clear();
                String sql = "SELECT phongdachon, hovaten, sodienthoai, tienthue FROM thongtinkhach";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        String room = rs.getString("phongdachon");
                        String name = rs.getString("hovaten");
                        String phone = rs.getString("sodienthoai");
                        String rental = rs.getString("tienthue");

                        // Thêm dữ liệu vào bảng (JTable)
                        bangmodel.addRow(new Object[]{room, name, phone, rental, "Chi tiết"});

                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void hienhoadon() {
            String u = "jdbc:mysql://127.0.0.1:3306/login";
            String r = "root";
            String pa = "1412006";

            try (Connection conn = DriverManager.getConnection(u, r, pa)) {
                tablemodel.setRowCount(0);

                String sql = "SELECT STT, phong, sodiencu, sodienmoi, sonuoccu, sonuocmoi, tongtienphong, ngaycu, ngaymoi, trangthai FROM login.hoadon";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        String stt = rs.getString("STT");
                        String p = rs.getString("phong");
                        String sdc = rs.getString("sodiencu");
                        String sdm = rs.getString("sodienmoi");
                        String snc = rs.getString("sonuoccu");
                        String snm = rs.getString("sonuocmoi");
                        String ttp = rs.getString("tongtienphong");
                        String nc = rs.getString("ngaycu");
                        String nm = rs.getString("ngaymoi");
                        String tt = rs.getString("trangthai");

                        tablemodel.addRow(new Object[]{stt,p, sdc, sdm, snc, snm, ttp, nc, nm, tt});
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }



        private void deleteRecord(String idphong) {
            String url = "jdbc:mysql://127.0.0.1:3306/login";
            String user = "root";
            String password = "1412006";
            int option = JOptionPane.showConfirmDialog(null, "Xác nhận xóa phòng này!", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {

                String sql = "DELETE FROM login.thongtinkhach WHERE phongdachon = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, idphong);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy bản ghi!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
            }
        }
        // Lớp ButtonRenderer để hiển thị JButton trong cột
        static class ButtonRenderer extends JButton implements TableCellRenderer {
            public ButtonRenderer() {
                setText("Chi tiết");
                setFont(new Font("Arial", Font.BOLD, 10));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return this;
            }
        }

//PHẦN THÊM JBUTTON CHI TIẾT
        static class ButtonEditor extends DefaultCellEditor {
            protected JButton button;
            private String label;
            private boolean isPushed;



            public ButtonEditor(JCheckBox checkBox) {
                super(checkBox);
                button = new JButton();
                button.setOpaque(true);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        fireEditingStopped();  // Dừng quá trình chỉnh sửa
                        JFrame chitietframe = new JFrame();
                        chitietframe.setTitle("Chi tiết");
                        chitietframe.setSize(320, 450);
                        chitietframe.setResizable(false);
                        chitietframe.setLayout(null);
                        chitietframe.setLocationRelativeTo(null);
                        chitietframe.getContentPane().setBackground(Color.PINK);

                        RoundedPanel ttchitiet = new RoundedPanel(40);
                        ttchitiet.setLayout(null);
                        ttchitiet.setPreferredSize(new Dimension(100, 100));
                        ttchitiet.setBounds(10, 10, 286, 394);
                        ttchitiet.setBackground(Color.WHITE);
                        chitietframe.add(ttchitiet);

                        ImageIcon iconngoinha = new ImageIcon("C:/Users/ADMIN/Pictures/Icon/iconngoinha.png");
                        Image kticon = iconngoinha.getImage().getScaledInstance(512, 512, Image.SCALE_SMOOTH);
                        chitietframe.setIconImage(kticon);

                        bang.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                // Lấy chỉ số hàng và cột từ vị trí chuột
                                int ro = bang.rowAtPoint(e.getPoint());



                                value = (String)bang.getValueAt(ro, 0);

                                JLabel hphong = new JLabel(value);
                                hphong.setFont(new Font("Arial", Font.BOLD, 20));
                                hphong.setBounds(115, 10, 100, 22);
                                ttchitiet.add(hphong);

                                String ur = "jdbc:mysql://127.0.0.1:3306/login";
                                String us = "root";
                                String pas = "1412006";


                                String xuat = value;

                                String sql = "SELECT phongdachon, hovaten, sodienthoai, quequano, socccd, thuengay, tiendien, tiennuoc, tienthue FROM thongtinkhach WHERE phongdachon = ?";
                                try (Connection conn = DriverManager.getConnection(ur, us, pas)) {
                                    // Sử dụng PreparedStatement để thay thế ? bằng giá trị của 'value'
                                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                                        ps.setString(1, xuat); // Thay thế ? bằng giá trị 'value'

                                        try (ResultSet rs = ps.executeQuery()) {
                                            // Duyệt qua từng hàng dữ liệu trong ResultSet
                                            while (rs.next()) {
                                                // Lấy dữ liệu từ các cột của bảng trong cơ sở dữ liệu
                                                String name = rs.getString("hovaten");
                                                String phone = rs.getString("sodienthoai");
                                                String hometown = rs.getString("quequano");
                                                String id = rs.getString("socccd");
                                                String dailyrent = rs.getString("thuengay");
                                                String electricity = rs.getString("tiendien");
                                                String water = rs.getString("tiennuoc");
                                                String rental = rs.getString("tienthue");

                                                JLabel thongtinlienhe = new JLabel("Thông tin liên hệ");
                                                thongtinlienhe.setFont(new Font("Arial", Font.BOLD, 14));
                                                thongtinlienhe.setBounds(5, 40, 150, 20);
                                                ttchitiet.add(thongtinlienhe);
                                             //PHẦN HỌ TÊN
                                                JLabel tenlabel = new JLabel("Họ và tên");
                                                tenlabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                tenlabel.setBounds(5, 65, 100, 20);
                                                ttchitiet.add(tenlabel);

                                                JSeparator knten = new JSeparator(SwingConstants.HORIZONTAL);
                                                knten.setForeground(Color.LIGHT_GRAY);
                                                knten.setBounds(5, 85, 276, 20);
                                                ttchitiet.add(knten);

                                                JLabel tenn = new JLabel(name);
                                                tenn.setFont(new Font("Arial", Font.BOLD, 12));
                                                // Tính toán chiều rộng của văn bản
                                                FontMetrics metrics = tenn.getFontMetrics(tenn.getFont());
                                                int rvanban = metrics.stringWidth(name);
                                                // Đặt kích thước cho JLabel
                                                tenn.setPreferredSize(new Dimension(rvanban + 20, tenn.getPreferredSize().height));  // Cộng thêm khoảng cách padding nếu cần
                                                // Đặt vị trí của JLabel trong container
                                                int rpanel = ttchitiet.getWidth();  // Lấy chiều rộng của JPanel (container)
                                                int x = rpanel - rvanban - 10;  // Căn lề phải (khoảng cách 10px từ cạnh phải)
                                                tenn.setBounds(x, 65, rvanban + 20, 20);  // Đặt vị trí và kích thước của JLabel
                                                ttchitiet.add(tenn);

                                             //PHẦN SỐ ĐIỆN THOẠI
                                                JLabel sdtlabel = new JLabel("Số điện thoại");
                                                sdtlabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                sdtlabel.setBounds(5, 90, 100, 20);
                                                ttchitiet.add(sdtlabel);

                                                JSeparator knsdt = new JSeparator(SwingConstants.HORIZONTAL);
                                                knsdt.setForeground(Color.LIGHT_GRAY);
                                                knsdt.setBounds(5, 110, 276, 20);
                                                ttchitiet.add(knsdt);

                                                JLabel sdtt = new JLabel(phone);
                                                sdtt.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics1 = sdtt.getFontMetrics(sdtt.getFont());
                                                int rvanban1 = metrics1.stringWidth(phone);
                                                sdtt.setPreferredSize(new Dimension(rvanban1 + 20, sdtt.getPreferredSize().height));
                                                int rpanel1 = ttchitiet.getWidth();
                                                int x1 = rpanel1 - rvanban1 - 4;
                                                sdtt.setBounds(x1, 90, rvanban1 + 20, 20);
                                                ttchitiet.add(sdtt);

                                             //PHẦN QUÊ QUÁN
                                                JLabel quequanlabel = new JLabel("Quê quán/Địa chỉ");
                                                quequanlabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                quequanlabel.setBounds(5, 115, 100, 20);
                                                ttchitiet.add(quequanlabel);

                                                JSeparator knquequan = new JSeparator(SwingConstants.HORIZONTAL);
                                                knquequan.setForeground(Color.LIGHT_GRAY);
                                                knquequan.setBounds(5, 134, 276, 20);
                                                ttchitiet.add(knquequan);

                                                JLabel quequann = new JLabel(hometown);
                                                quequann.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics2 = quequann.getFontMetrics(quequann.getFont());
                                                int rvanban2 = metrics2.stringWidth(hometown);
                                                quequann.setPreferredSize(new Dimension(rvanban2 + 20, quequann.getPreferredSize().height));
                                                int rpanel2 = ttchitiet.getWidth();
                                                int x2 = rpanel2 - rvanban2 - 10;
                                                quequann.setBounds(x2, 115, rvanban2 + 20, 20);
                                                ttchitiet.add(quequann);

                                             //PHẦN CCCD
                                                JLabel idlabel = new JLabel("CCCD");
                                                idlabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                idlabel.setBounds(5, 140, 100, 20);
                                                ttchitiet.add(idlabel);

                                                JSeparator knid = new JSeparator(SwingConstants.HORIZONTAL);
                                                knid.setForeground(Color.LIGHT_GRAY);
                                                knid.setBounds(5, 160, 276, 20);
                                                ttchitiet.add(knid);

                                                JLabel idd = new JLabel(id);
                                                idd.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics3 = idd.getFontMetrics(idd.getFont());
                                                int rvanban3 = metrics3.stringWidth(id);
                                                idd.setPreferredSize(new Dimension(rvanban3 + 20, idd.getPreferredSize().height));
                                                int rpanel3 = ttchitiet.getWidth();
                                                int x3 = rpanel3 - rvanban3 - 4;
                                                idd.setBounds(x3, 140, rvanban3 + 20, 20);
                                                ttchitiet.add(idd);

                                             //PHẦN NGÀY THUÊ
                                                JLabel ngaythuelabel = new JLabel("Ngày thuê");
                                                ngaythuelabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                ngaythuelabel.setBounds(5, 165, 100, 20);
                                                ttchitiet.add(ngaythuelabel);

                                                JSeparator knngaythue = new JSeparator(SwingConstants.HORIZONTAL);
                                                knngaythue.setForeground(Color.LIGHT_GRAY);
                                                knngaythue.setBounds(5, 185, 276, 20);
                                                ttchitiet.add(knngaythue);

                                                JLabel ngaythuee = new JLabel(dailyrent);
                                                ngaythuee.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics4 = ngaythuee.getFontMetrics(ngaythuee.getFont());
                                                int rvanban4 = metrics4.stringWidth(dailyrent);
                                                ngaythuee.setPreferredSize(new Dimension(rvanban + 20, ngaythuee.getPreferredSize().height));
                                                int rpanel4 = ttchitiet.getWidth();
                                                int x4 = rpanel4 - rvanban4 - 5;
                                                ngaythuee.setBounds(x4, 165, rvanban4 + 20, 20);
                                                ttchitiet.add(ngaythuee);
                                             //THÔNG TIN GIÁ THUÊ
                                                JLabel Tthongtihgiathue = new JLabel("Thông tin giá thuê");
                                                Tthongtihgiathue.setFont(new Font("Arial", Font.BOLD, 14));
                                                Tthongtihgiathue.setBounds(5, 190, 150, 20);
                                                ttchitiet.add(Tthongtihgiathue);

                                             //PHẦN TIỀN ĐIỆN
                                                JLabel tiendienlabel = new JLabel("Tiền điện (đ/kWh)");
                                                tiendienlabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                tiendienlabel.setBounds(5, 215, 100, 20);
                                                ttchitiet.add(tiendienlabel);

                                                JSeparator kntiendien = new JSeparator(SwingConstants.HORIZONTAL);
                                                kntiendien.setForeground(Color.LIGHT_GRAY);
                                                kntiendien.setBounds(5, 236, 276, 20);
                                                ttchitiet.add(kntiendien);

                                                JLabel tiendienn = new JLabel(electricity);
                                                tiendienn.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics5 = tiendienn.getFontMetrics(tiendienn.getFont());
                                                int rvanban5 = metrics5.stringWidth(electricity);
                                                tiendienn.setPreferredSize(new Dimension(rvanban + 20, tiendienn.getPreferredSize().height));
                                                int rpanel5 = ttchitiet.getWidth();
                                                int x5 = rpanel5 - rvanban5 - 5;
                                                tiendienn.setBounds(x5, 215, rvanban5 + 20, 20);
                                                ttchitiet.add(tiendienn);

                                             //PHẦN TIỀN NƯỚC
                                                JLabel tiennuoclabel = new JLabel("Tiền nước (đ/khối)");
                                                tiennuoclabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                tiennuoclabel.setBounds(5, 240, 100, 20);
                                                ttchitiet.add(tiennuoclabel);

                                                JSeparator kntiennuoc = new JSeparator(SwingConstants.HORIZONTAL);
                                                kntiennuoc.setForeground(Color.LIGHT_GRAY);
                                                kntiennuoc.setBounds(5, 260, 276, 20);
                                                ttchitiet.add(kntiennuoc);

                                                JLabel tiennuocc = new JLabel(water);
                                                tiennuocc.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics6 = tiennuocc.getFontMetrics(tiennuocc.getFont());
                                                int rvanban6 = metrics6.stringWidth(water);
                                                tiennuocc.setPreferredSize(new Dimension(rvanban6 + 20, tiennuocc.getPreferredSize().height));
                                                int rpanel6 = ttchitiet.getWidth();
                                                int x6 = rpanel6 - rvanban6 - 5;
                                                tiennuocc.setBounds(x6, 240, rvanban6 + 20, 20);
                                                ttchitiet.add(tiennuocc);

                                             //PHẦN GIÁ THUÊ
                                                JLabel giathuelabel = new JLabel("Giá thuê (đ/tháng)");
                                                giathuelabel.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
                                                giathuelabel.setBounds(5, 265, 100, 20);
                                                ttchitiet.add(giathuelabel);

                                                JSeparator kngiathue = new JSeparator(SwingConstants.HORIZONTAL);
                                                kngiathue.setForeground(Color.LIGHT_GRAY);
                                                kngiathue.setBounds(5, 285, 276, 20);
                                                ttchitiet.add(kngiathue);

                                                JLabel giathuee = new JLabel(rental);
                                                giathuee.setFont(new Font("Arial", Font.BOLD, 12));
                                                FontMetrics metrics7 = giathuee.getFontMetrics(giathuee.getFont());
                                                int rvanban7 = metrics7.stringWidth(rental);
                                                giathuee.setPreferredSize(new Dimension(rvanban7 + 20, giathuee.getPreferredSize().height));
                                                int rpanel7 = ttchitiet.getWidth();
                                                int x7 = rpanel7 - rvanban7 - 5;
                                                giathuee.setBounds(x7, 265, rvanban7 + 20, 20);
                                                ttchitiet.add(giathuee);

                                                //Phần JButton
                                                JButton taohoadon = new JButton("Tạo hóa đơn") {
                                                    @Override
                                                    protected void paintComponent(Graphics g) {
                                                        if (getModel().isPressed()) {
                                                            g.setColor(new Color(0, 100, 0)); // Màu khi bấm
                                                        } else {
                                                            g.setColor(new Color(0, 128, 0)); // Màu nền
                                                        }
                                                        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Vẽ hình chữ nhật bo góc
                                                        super.paintComponent(g);
                                                    }
                                                };
                                                taohoadon.setFont(new Font("Arial", Font.BOLD, 14));
                                                taohoadon.setForeground(Color.WHITE); // Màu chữ
                                                taohoadon.setBounds(83, 320, 125, 30);
                                                taohoadon.setFocusPainted(false);
                                                taohoadon.setContentAreaFilled(false); // Không tô màu vùng nội dung
                                                taohoadon.setBorderPainted(false); // Không vẽ viền
                                                ttchitiet.add(taohoadon);
                                                taohoadon.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        ttchitiet.removeAll();
                                                        ttchitiet.revalidate();
                                                        ttchitiet.repaint();
                                                        JLabel hdcphong = new JLabel(value);
                                                        hdcphong.setFont(new Font("Arial", Font.BOLD, 20));
                                                        hdcphong.setBounds(115, 10, 100, 22);
                                                        ttchitiet.add(hdcphong);

                                                        JLabel thongtincu = new JLabel("Thông tin điện nước cũ");
                                                        thongtincu.setFont(new Font("Arial", Font.BOLD, 14));
                                                        thongtincu.setBounds(5, 40, 180, 20);
                                                        ttchitiet.add(thongtincu);

                                                        JLabel thoigiancu = new JLabel("Thời gian ghi nhận:");
                                                        thoigiancu.setFont(new Font("Arial", Font.BOLD, 12));
                                                        thoigiancu.setBounds(5, 65, 180, 20);
                                                        ttchitiet.add(thoigiancu);

                                                        String[] nam1 = {"2030", "2029", "2028", "2027", "2026", "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015"};
                                                        JComboBox namcu = new JComboBox(nam1);
                                                        namcu.setBounds(125, 65, 55, 20);
                                                        ttchitiet.add(namcu);
                                                        String[] thang1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                        JComboBox thangcu = new JComboBox(thang1);
                                                        thangcu.setBounds(180, 65, 40, 20);
                                                        ttchitiet.add(thangcu);
                                                        JComboBox ngaycu1 = new JComboBox();
                                                        ngaycu1.setBounds(220, 65, 40, 20);
                                                        ttchitiet.add(ngaycu1);

                                                        ActionListener capNhatNgay = new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                int month = Integer.parseInt((String) thangcu.getSelectedItem());
                                                                int year = Integer.parseInt((String) namcu.getSelectedItem());
                                                                int songay = 0;
                                                                boolean xdnam = false;
                                                                if ((year % 4 == 0 && year % 100 != 00) || (year % 100 == 0 && year % 400 == 0)) {
                                                                    xdnam = true;
                                                                } else if (year % 100 == 0 && year % 400 != 0) {
                                                                    xdnam = false;
                                                                }
                                                                switch (month) {
                                                                    case 1:
                                                                    case 3:
                                                                    case 5:
                                                                    case 7:
                                                                    case 8:
                                                                    case 10:
                                                                    case 12:
                                                                        songay = 31;
                                                                        break;
                                                                    case 4:
                                                                    case 6:
                                                                    case 9:
                                                                    case 11:
                                                                        songay = 30;
                                                                        break;
                                                                    case 2:
                                                                        if (xdnam) {
                                                                            songay = 29;
                                                                        } else {
                                                                            songay = 28;
                                                                        }
                                                                }

                                                                ngaycu1.removeAllItems();
                                                                for (int i = 1; i <= songay; i++) {
                                                                    ngaycu1.addItem(String.valueOf(i));
                                                                }
                                                            }
                                                        };
                                                        thangcu.addActionListener(capNhatNgay);
                                                        namcu.addActionListener(capNhatNgay);
                                                        capNhatNgay.actionPerformed(null);

                                                        JLabel sodiencu = new JLabel("Số điện cũ");
                                                        sodiencu.setFont(new Font("Arial", Font.BOLD, 12));
                                                        sodiencu.setBounds(30, 90, 180, 20);
                                                        ttchitiet.add(sodiencu);

                                                        JTextField sodiencu1 = new JTextField();
                                                        sodiencu1.setBounds(30, 110, 68, 25);
                                                        ttchitiet.add(sodiencu1);

                                                        JLabel sonuoccu = new JLabel("Số nước cũ");
                                                        sonuoccu.setFont(new Font("Arial", Font.BOLD, 12));
                                                        sonuoccu.setBounds(186, 90, 180, 20);
                                                        ttchitiet.add(sonuoccu);

                                                        JTextField sonuoccu1 = new JTextField();
                                                        sonuoccu1.setBounds(186, 110, 68, 25);
                                                        ttchitiet.add(sonuoccu1);

                                                        /////////////////////////////////////////////////////////////////////

                                                        JLabel thongtinmoi = new JLabel("Thông tin điện nước mới");
                                                        thongtinmoi.setFont(new Font("Arial", Font.BOLD, 14));
                                                        thongtinmoi.setBounds(5, 140, 180, 20);
                                                        ttchitiet.add(thongtinmoi);

                                                        JLabel thoigianmoi = new JLabel("Thời gian ghi nhận:");
                                                        thoigianmoi.setFont(new Font("Arial", Font.BOLD, 12));
                                                        thoigianmoi.setBounds(5, 165, 180, 20);
                                                        ttchitiet.add(thoigianmoi);

                                                        String[] nam2 = {"2030", "2029", "2028", "2027", "2026", "2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015"};
                                                        JComboBox nammoi = new JComboBox(nam1);
                                                        nammoi.setBounds(125, 165, 55, 20);
                                                        ttchitiet.add(nammoi);
                                                        String[] thang2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                                                        JComboBox thangmoi = new JComboBox(thang1);
                                                        thangmoi.setBounds(180, 165, 40, 20);
                                                        ttchitiet.add(thangmoi);
                                                        JComboBox ngaymoi1 = new JComboBox();
                                                        ngaymoi1.setBounds(220, 165, 40, 20);
                                                        ttchitiet.add(ngaymoi1);

                                                        ActionListener capnhatngay = new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                int month = Integer.parseInt((String) thangmoi.getSelectedItem());
                                                                int year = Integer.parseInt((String) nammoi.getSelectedItem());
                                                                int songay = 0;
                                                                boolean xdnam = false;
                                                                if ((year % 4 == 0 && year % 100 != 00) || (year % 100 == 0 && year % 400 == 0)) {
                                                                    xdnam = true;
                                                                } else if (year % 100 == 0 && year % 400 != 0) {
                                                                    xdnam = false;
                                                                }
                                                                switch (month) {
                                                                    case 1:
                                                                    case 3:
                                                                    case 5:
                                                                    case 7:
                                                                    case 8:
                                                                    case 10:
                                                                    case 12:
                                                                        songay = 31;
                                                                        break;
                                                                    case 4:
                                                                    case 6:
                                                                    case 9:
                                                                    case 11:
                                                                        songay = 30;
                                                                        break;
                                                                    case 2:
                                                                        if (xdnam) {
                                                                            songay = 29;
                                                                        } else {
                                                                            songay = 28;
                                                                        }
                                                                }

                                                                ngaymoi1.removeAllItems();
                                                                for (int i = 1; i <= songay; i++) {
                                                                    ngaymoi1.addItem(String.valueOf(i));
                                                                }
                                                            }
                                                        };
                                                        thangmoi.addActionListener(capnhatngay);
                                                        nammoi.addActionListener(capnhatngay);
                                                        capnhatngay.actionPerformed(null);

                                                        JLabel sodienmoi = new JLabel("Số điện mới");
                                                        sodienmoi.setFont(new Font("Arial", Font.BOLD, 12));
                                                        sodienmoi.setBounds(30, 190, 180, 20);
                                                        ttchitiet.add(sodienmoi);

                                                        JTextField sodienmoi1 = new JTextField();
                                                        sodienmoi1.setBounds(30, 210, 68, 25);
                                                        ttchitiet.add(sodienmoi1);

                                                        JLabel sonuocmoi = new JLabel("Số nước mới");
                                                        sonuocmoi.setFont(new Font("Arial", Font.BOLD, 12));
                                                        sonuocmoi.setBounds(186, 190, 180, 20);
                                                        ttchitiet.add(sonuocmoi);

                                                        JTextField sonuocmoi1 = new JTextField();
                                                        sonuocmoi1.setBounds(186, 210, 68, 25);
                                                        ttchitiet.add(sonuocmoi1);

                                                        JLabel tongtien = new JLabel("Tổng tiền: ");
                                                        tongtien.setFont(new Font("Arial", Font.BOLD, 12));
                                                        tongtien.setBounds(5, 250, 70, 20);
                                                        ttchitiet.add(tongtien);

                                                        JLabel tthai = new JLabel("Trạng thái: ");
                                                        tthai.setFont(new Font("Arial", Font.BOLD, 12));
                                                        tthai.setBounds(5, 275, 70, 20);
                                                        ttchitiet.add(tthai);

                                                        String[] check = {"Chưa thanh toán", "Đã thanh toán"};
                                                        JComboBox check1 = new JComboBox(check);
                                                        check1.setBounds(75, 275, 70, 20);
                                                        ttchitiet.add(check1);




                                                        JButton taohoadon1 = new JButton("In hóa đơn") {
                                                            @Override
                                                            protected void paintComponent(Graphics g) {
                                                                if (getModel().isPressed()) {
                                                                    g.setColor(new Color(0, 100, 0)); // Màu khi bấm
                                                                } else {
                                                                    g.setColor(new Color(0, 128, 0)); // Màu nền
                                                                }
                                                                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Vẽ hình chữ nhật bo góc
                                                                super.paintComponent(g);
                                                            }
                                                        };
                                                        taohoadon1.setFont(new Font("Arial", Font.BOLD, 14));
                                                        taohoadon1.setForeground(Color.WHITE); // Màu chữ
                                                        taohoadon1.setBounds(83, 320, 125, 30);
                                                        taohoadon1.setFocusPainted(false);
                                                        taohoadon1.setContentAreaFilled(false); // Không tô màu vùng nội dung
                                                        taohoadon1.setBorderPainted(false); // Không vẽ viền
                                                        ttchitiet.add(taohoadon1);

                                                        taohoadon1.addActionListener(new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {


                                                                JLabel hienthitongtien = new JLabel(tong);
                                                                hienthitongtien.setFont(new Font("Arial",  Font.ROMAN_BASELINE, 12));
                                                                hienthitongtien.setBounds(65, 250, 180, 20);
                                                                ttchitiet.add(hienthitongtien);
                                                                try {
                                                                    // Lấy giá trị từ các trường nhập liệu
                                                                    int diencu = Integer.parseInt(sodiencu1.getText());
                                                                    int nuoccu = Integer.parseInt(sonuoccu1.getText());
                                                                    int dienmoi = Integer.parseInt(sodienmoi1.getText());
                                                                    int nuocmoi = Integer.parseInt(sonuocmoi1.getText());
                                                                    int giadienn = Integer.parseInt(electricity);
                                                                    int gianuocc = Integer.parseInt(water);
                                                                    int giathuee = Integer.parseInt(rental);

                                                                    int dienn = (dienmoi - diencu) * giadienn;
                                                                    int nuocc = (nuocmoi - nuoccu) * gianuocc;
                                                                    int tongtienn = dienn + nuocc + giathuee;



                                                                    DecimalFormat formatter = new DecimalFormat("#,###");
                                                                    tong = formatter.format(tongtienn);
                                                                    hienthitongtien.setText(tong);

                                                                } catch (NumberFormatException ex) {
                                                                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                                                } catch (Exception ex) {
                                                                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                                                }

                                                                JLabel vnd = new JLabel("VNĐ");
                                                                vnd.setFont(new Font("Arial", Font.BOLD, 12));
                                                                vnd.setBounds(125, 250, 70, 20);
                                                                ttchitiet.add(vnd);


                                                                String hovaten = name;
                                                                String sodienthoai = phone;
                                                                String trangthai = (String) check1.getSelectedItem();
                                                                String phong = value;
                                                                String sodiencu = sodiencu1.getText();
                                                                String sonuoccu = sonuoccu1.getText();
                                                                String sodienmoi = sodienmoi1.getText();
                                                                String sonuocmoi = sonuocmoi1.getText();
                                                                String ngaycu = (String) namcu.getSelectedItem() + "-" + (String) thangcu.getSelectedItem() + "-" + (String) ngaycu1.getSelectedItem();
                                                                String ngaymoi = (String) nammoi.getSelectedItem() + "-" + (String) thangmoi.getSelectedItem() + "-" + (String) ngaymoi1.getSelectedItem();
                                                                String tongtienphong = String.valueOf(tong);
                                                                String sott;

                                                                try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login", "root", "1412006")) {
                                                                    // Lấy STT lớn nhất hiện tại
                                                                    String sql = "SELECT MAX(STT) AS max_stt FROM hoadon";
                                                                    try (PreparedStatement ps = conn.prepareStatement(sql);
                                                                         ResultSet rs = ps.executeQuery()) {

                                                                        int so = 1;
                                                                        if (rs.next()) {

                                                                            so = rs.getInt("max_stt") + 1;
                                                                        }

                                                                        String insertSql = "INSERT INTO hoadon (STT, phong, hovaten, sodienthoai, sodiencu, sonuoccu, sodienmoi, sonuocmoi, tongtienphong, ngaycu, ngaymoi, trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                                                        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                                                                            stmt.setInt(1, so);  // Gán giá trị STT mới
                                                                            stmt.setString(2, phong);
                                                                            stmt.setString(3, hovaten);
                                                                            stmt.setString(4, sodienthoai);
                                                                            stmt.setString(5, sodiencu);
                                                                            stmt.setString(6, sonuoccu);
                                                                            stmt.setString(7, sodienmoi);
                                                                            stmt.setString(8, sonuocmoi);
                                                                            stmt.setString(9, tongtienphong);
                                                                            stmt.setString(10, ngaycu);
                                                                            stmt.setString(11, ngaymoi);
                                                                            stmt.setString(12, trangthai);

                                                                            // Thực thi câu lệnh SQL
                                                                            int rowsAffected = stmt.executeUpdate();
                                                                            if (rowsAffected > 0) {
                                                                                JOptionPane.showMessageDialog(null, "Thêm thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                                                                            }
                                                                        }
                                                                    }
                                                                } catch (SQLException ex) {
                                                                    ex.printStackTrace();
                                                                    JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                                                }



                                                                chitietframe.setVisible(false);
                                                            }

                                                        });









                                                    }
                                                });






                                                ttchitiet.revalidate();
                                                ttchitiet.repaint();

                                            }
                                        }
                                    }

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }


                            }

                        });





                        chitietframe.setVisible(true);
                    }
                });




            }

//TẠO PANEL BO GÓC
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

            // Vẽ hình chữ nhật bo góc
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        }
    }
    //phần phụ của nút bấm chi tiết
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                label = (value == null) ? "" : value.toString();
                button.setText(label);
                isPushed = true;
                return button;
            }
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Câu lệnh xử lý sự kiện nhấn nút
            isPushed = false;
            return label;
        }
        return null;
    }
        }
    }
    public static void main(String[] args) {
        new motel();
    }
}