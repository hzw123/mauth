package cn.mauth.ccrm.server.set;

import cn.mauth.ccrm.core.domain.set.SetProduct;
import cn.mauth.ccrm.rep.set.SetProductRepository;
import cn.mauth.ccrm.server.BaseServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServer extends BaseServer<SetProduct,SetProductRepository> {
    public ProductServer(SetProductRepository repository) {
        super(repository);
    }

    public List<SetProduct> findByProductCategoryId(int productCategoryId) {
        return findAll((root, query, cb) -> {
            return cb.equal(root.join("productcategory").get("dbid"),productCategoryId);
        });
    }
}
