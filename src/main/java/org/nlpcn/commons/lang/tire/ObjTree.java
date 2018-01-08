package org.nlpcn.commons.lang.tire;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ansj on 25/05/2017.数组对象的tire树
 */
public class ObjTree<T> implements Serializable {

	private Map<Object, ObjTree<T>> branches;

	private T obj;

	private int status;

	/**
	 * 增加对象数组到树种
	 *
	 * @param objs
	 */
	public void add(T t, Object... objs) {
		ObjTree<T> objTree = this;
		int len = objs.length;
		for (int i = 0; i < len; i++) {
			objTree = objTree.getOrCreateObjTree(objs[i]);
			if (objTree.status == 3) {
				objTree.status = 2;
			} else if (objTree.status == 0) {
				objTree.status = 1;
			}
		}
		objTree.setObj(t);

		if (objTree.status == 1) {
			objTree.status = 2;
		} else if (objTree.status == 0) {
			objTree.status = 3;
		}
	}


	/**
	 * 取得当前分支下所有的截止值
	 *
	 * @param objs
	 */
	public T get(Object... objs) {
		ObjTree<T> objTree = getObjTree(objs);
		if (objTree == null) {
			return null;
		}
		return objTree.getObj();
	}

	/**
	 * 取得当然分支的节点
	 *
	 * @param objs
	 * @return
	 */
	public ObjTree<T> getObjTree(Object... objs) {
		ObjTree objTree = this;
		for (int i = 0; i < objs.length; i++) {
			objTree = objTree.getObjTree(objs[i]);
			if (objTree == null) {
				return null;
			}
		}
		return objTree;
	}

	/**
	 * 判断一个路径是否存在.且是截止
	 *
	 * @param objs
	 * @return
	 */
	public boolean contains(Object... objs) {
		ObjTree objTree = this;
		for (int i = 0; i < objs.length; i++) {
			objTree = objTree.getObjTree(objs[i]);
			if (objTree == null) {
				return false;
			}
		}

		if (objTree.status > 1) {
			return true;
		} else {
			return false;
		}
	}

	private ObjTree getObjTree(Object obj) {
		if (branches == null) {
			return null;
		}
		return branches.get(obj);
	}


	/**
	 * 获得对象树,如果不存在则创建一个
	 *
	 * @param obj
	 * @return
	 */
	private ObjTree<T> getOrCreateObjTree(Object obj) {
		if (this.branches == null) {
			this.branches = new HashMap<Object, ObjTree<T>>();
		}
		ObjTree<T> objTree = this.branches.get(obj);
		if (objTree == null) {
			objTree = new ObjTree();
			this.branches.put(obj, objTree);
		}
		return objTree;
	}

	public Map<Object, ObjTree<T>> getBranches() {
		return branches;
	}

	public int getStatus() {
		return status;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
