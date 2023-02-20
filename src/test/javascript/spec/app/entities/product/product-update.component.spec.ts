/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProductUpdateComponent from '@/entities/product/product-update.vue';
import ProductClass from '@/entities/product/product-update.component';
import ProductService from '@/entities/product/product.service';

import PictureService from '@/entities/picture/picture.service';

import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';

import CommentService from '@/entities/comment/comment.service';

import AttributeService from '@/entities/attribute/attribute.service';

import ItemService from '@/entities/item/item.service';

import CategoryService from '@/entities/category/category.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Product Management Update Component', () => {
    let wrapper: Wrapper<ProductClass>;
    let comp: ProductClass;
    let productServiceStub: SinonStubbedInstance<ProductService>;

    beforeEach(() => {
      productServiceStub = sinon.createStubInstance<ProductService>(ProductService);

      wrapper = shallowMount<ProductClass>(ProductUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          productService: () => productServiceStub,
          alertService: () => new AlertService(),

          pictureService: () =>
            sinon.createStubInstance<PictureService>(PictureService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          productMainAttributeService: () =>
            sinon.createStubInstance<ProductMainAttributeService>(ProductMainAttributeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          commentService: () =>
            sinon.createStubInstance<CommentService>(CommentService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          attributeService: () =>
            sinon.createStubInstance<AttributeService>(AttributeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          itemService: () =>
            sinon.createStubInstance<ItemService>(ItemService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          categoryService: () =>
            sinon.createStubInstance<CategoryService>(CategoryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.product = entity;
        productServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.product = entity;
        productServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProduct = { id: 123 };
        productServiceStub.find.resolves(foundProduct);
        productServiceStub.retrieve.resolves([foundProduct]);

        // WHEN
        comp.beforeRouteEnter({ params: { productId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.product).toBe(foundProduct);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
