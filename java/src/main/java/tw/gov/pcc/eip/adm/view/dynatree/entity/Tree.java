package tw.gov.pcc.eip.adm.view.dynatree.entity;


public class Tree {
	
	private String id;
	private String name;
	private String display;
	private String title;
	private String parentId;
	private int level;
	private boolean folder = false;
	private boolean checked = false;
	private String cssClass;
	private Child child;
	
	public Tree() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public static class Child {
		
		private String icon;
		private String cssClass;
		private boolean hideCheckbox = false;
		private boolean unselectable = false;
		
		public Child() {}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getCssClass() {
			return cssClass;
		}

		public void setCssClass(String cssClass) {
			this.cssClass = cssClass;
		}

		public boolean isHideCheckbox() {
			return hideCheckbox;
		}

		public void setHideCheckbox(boolean hideCheckbox) {
			this.hideCheckbox = hideCheckbox;
		}

		public boolean isUnselectable() {
			return unselectable;
		}

		public void setUnselectable(boolean unselectable) {
			this.unselectable = unselectable;
		}
	}
}