/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProductMainAttributeUpdateComponent from '@/entities/product-main-attribute/product-main-attribute-update.vue';
import ProductMainAttributeClass from '@/entities/product-main-attribute/product-main-attribute-update.component';
import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';

import AttributeService from '@/entities/attribute/attribute.service';

import ProductService from '@/entities/product/product.service';
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
  describe('ProductMainAttribute Management Update Component', () => {
    let wrapper: Wrapper<ProductMainAttributeClass>;
    let comp: ProductMainAttributeClass;
    let productMainAttributeServiceStub: SinonStubbedInstance<ProductMainAttributeService>;

    beforeEach(() => {
      productMainAttributeServiceStub = sinon.createStubInstance<ProductMainAttributeService>(ProductMainAttributeService);

      wrapper = shallowMount<ProductMainAttributeClass>(ProductMainAttributeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          productMainAttributeService: () => productMainAttributeServiceStub,
          alertService: () => new AlertService(),

          attributeService: () =>
            sinon.createStubInstance<AttributeService>(AttributeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
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
        comp.productMainAttribute = entity;
        productMainAttributeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productMainAttributeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.productMainAttribute = entity;
        productMainAttributeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productMainAttributeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProductMainAttribute = { id: 123 };
        productMainAttributeServiceStub.find.resolves(foundProductMainAttribute);
        productMainAttributeServiceStub.retrieve.resolves([foundProductMainAttribute]);

        // WHEN
        comp.beforeRouteEnter({ params: { productMainAttributeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.productMainAttribute).toBe(foundProductMainAttribute);
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
