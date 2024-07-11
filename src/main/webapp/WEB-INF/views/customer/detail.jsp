<%@ page pageEncoding="utf-8"%>

<div class="my-4">
  <div class="row">
    <div class="col-6">
      <td><img src="/static/images/${prd.image}" class="product-image" alt="" /></td>
    </div>

    <div class="col-6 mt-5">
      <div class="product-detail-title">${prd.name}</div>
      <br />
      <table class="table">
        <tbody>
          <tr>
            <td>Loại sản phẩm:</td>
            <td>${prd.category.name}</td>
          </tr>
          <tr>
            <td>Giá bán:</td>
            <td><b>${prd.price} ₫</b></td>
          </tr>
        </tbody>
      </table>
      <br />
      <a class="btn btn-secondary" href="/">Quay lại</a>
      <a class="btn btn-primary" href="/add-to-cart/${prd.id}">Thêm vào giỏ hàng</a>
    </div>
  </div>
</div>