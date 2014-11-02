package br.unb.cic.iris.persistence.sqlite3;

import br.unb.cic.iris.core.model.IrisFolder;
import br.unb.cic.iris.persistence.IFolderDAO;

public class FolderDAO  extends AbstractDAO<IrisFolder> implements IFolderDAO {
	private static final String FIND_BY_NAME = 
			"FROM IrisFolder f "
			+ "where f.name = :pName";
}
