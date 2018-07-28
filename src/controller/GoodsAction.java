package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Shoppingdao;
import entity.Comment;
import entity.Goods;
import entity.GoodsKind;
import entity.Page;
import entity.User;
import service.GoodsService;
import service.imp.GoodsServiceImp;
import util.PageUtil;
import util.SearchKindUtil;

/**
 * Servlet implementation class GoodsAction
 */
@WebServlet("/GoodsAction")
public class GoodsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Shoppingdao idao = new Shoppingdao(); 	//��Ʒҵ���߼���Ķ���
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if(action.equals("ShowAllGoods")){//�鿴������Ʒ
			ShowAllGoods(request, response);
		}
		
		if(action.equals("SortGoods")){//����Ʒ��������
			SortGoods(request, response);
		}

		if(action.equals("SearchGoods")){//������Ʒ
			SearchGoods(request, response);
		}
		
		if(action.equals("ShowDetails")){//�鿴��Ʒ����
			ShowDetail(request, response);
		}
		
		if(action.equals("ShowGoodsByKind")){//����kindչʾ��Ʒ
			ShowGoodsByKind(request, response);
		}
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//����kindչʾ��Ʒ
	private void ShowGoodsByKind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡkid
		int kid = Integer.parseInt(request.getParameter("kid"));
		System.out.println(kid);
		GoodsKind gk = new GoodsKind();
		gk.setId(kid);
		
		GoodsService gs = new GoodsServiceImp();
		List<Goods> goodss = new ArrayList<>();
		goodss = gs.showGoodsByKind(gk);
		System.out.println(goodss);
		
		request.setAttribute("gks", goodss);
		request.getRequestDispatcher("kind.jsp").forward(request, response);
		
	}

	//�鿴��Ʒ����
	private void ShowDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		User user = (User) request.getSession().getAttribute("user");
		

		// �����ж��û��Ƿ��������Ʒ
		int flag = 0;
		List<Goods> goodss = new ArrayList<>();
		if(user!=null){
			goodss = idao.showGoodsById(user.getId());
		}
		System.out.println(goodss);
		for(Goods g:goodss){
			if(g.getId()==gid){
				flag = 1;
				break;
			}
		}
		
		
		GoodsService gs = new GoodsServiceImp();
		Goods goods = new Goods();
		goods.setId(gid);
		goods = gs.showGoodsDetail(goods);
		List<Comment> comments = new ArrayList<>();
		comments = idao.showCommentsByGoods(gid);
		for(Comment comment:comments){
			User u = new User();
			u = comment.getUser();
			u.setName(idao.selectUserById(u.getId()));	
			comment.setUser(u);
		}
	
		request.setAttribute("goods",goods);
		request.setAttribute("flag", flag);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("detail.jsp").forward(request, response);

		
	}

	//������Ʒ
	private void SearchGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡkey
		String key = null;
		key = request.getParameter("key");
		if(key!=null){
			key = new String(key.getBytes("ISO-8859-1"),"utf-8");
		}
		
		// ���÷�ҳ
		Page page = new Page();
		String strpage = request.getParameter("currentPage");
		int currentPage = 1;
		if(strpage!=null){
			currentPage = Integer.parseInt(strpage);
		}
		page.setCurrentPage(currentPage);
		page.setNum(3);
//		// �˴��޷���ȡtotal
		int total = PageUtil.total("t_goods", page.getNum());
		System.out.println(total);
		page.setTotal(total);
		
		// ��ȡ�������Ľ��
		GoodsService gs = new GoodsServiceImp();	
		List<Goods> search = new ArrayList<>();
		int size = 0;
		search = gs.searchGoodsAndSort(key,page);
		for(Goods goods:search){
			System.out.println(goods);
		}
		size = search.size();
		System.out.println(size);
		
		request.setAttribute("size", size);
		request.setAttribute("page", page);
		request.setAttribute("search", search);
		request.getRequestDispatcher("search.jsp").forward(request, response);
		
	}

	//����Ʒ��������
	private void SortGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡѡ�������
		String strsid = request.getParameter("sid");
		int sid = 3;
		if(strsid!=null){
			sid = Integer.parseInt(strsid);
			request.getSession().setAttribute("sid", sid);
		} else{
			sid = (int) request.getSession().getAttribute("sid");
		}
		String sort = null;
		if(sid==1){
			sort = "asc";
		} else {
			sort = "desc";
		}
		
		// ���÷�ҳ
		Page page = new Page();
		String strpage = request.getParameter("currentPage");
		int currentPage = 1;
		if(strpage!=null){
			currentPage = Integer.parseInt(strpage);
		}
		page.setCurrentPage(currentPage);
		page.setNum(3);
		int total = PageUtil.total("t_goods", page.getNum());
		page.setTotal(total);
		
		
		// �����ж��û��Ƿ�ѡ��������
		int sortFlag = 1;
		request.getSession().setAttribute("sortFlag", sortFlag);
		
		//��ȡ������goods
		GoodsService gs = new GoodsServiceImp();
		List<Goods> goodssSort = new ArrayList<>();
		goodssSort = gs.sortGoodsByPrice(sort, page);
		System.out.println(goodssSort);
		
		request.setAttribute("page", page);
		request.getSession().setAttribute("goodssSort", goodssSort);
		request.getRequestDispatcher("index.jsp").forward(request, response);

		
	}

	//�鿴������Ʒ
	private void ShowAllGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���÷�ҳ
		Page page = new Page();
		String strpage = request.getParameter("currentPage");
		int currentPage = 1;
		if(strpage!=null){
			currentPage = Integer.parseInt(strpage);
		}
		page.setCurrentPage(currentPage);
		page.setNum(3);
		int total = PageUtil.total("t_goods", page.getNum());
		page.setTotal(total);
		
		
		// ��ȡ������Ʒ
		GoodsService gs = new GoodsServiceImp();
		List<Goods> goodss = new ArrayList<>();
		goodss = gs.showAllGoodsAndSort(page);
		
		
		// ���������ܵ���Ʒ���
		List<GoodsKind> gks = SearchKindUtil.showAllkind();
		System.out.println(gks);
		
		// �����ж��û��Ƿ�ѡ��������
		int sortFlag = 0;
		
		request.getSession().setAttribute("sortFlag", sortFlag);
		request.setAttribute("page", page);
		request.getSession().setAttribute("goodskind", gks);
		request.getServletContext().setAttribute("goodss", goodss);
		request.getRequestDispatcher("index.jsp").forward(request, response);

		
	}

}
