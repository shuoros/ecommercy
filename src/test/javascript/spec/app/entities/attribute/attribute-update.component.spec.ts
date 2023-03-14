/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AttributeUpdateComponent from '@/entities/attribute/attribute-update.vue';
import AttributeClass from '@/entities/attribute/attribute-update.component';
import AttributeService from '@/entities/attribute/attribute.service';

import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';

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
  describe('Attribute Management Update Component', () => {
    let wrapper: Wrapper<AttributeClass>;
    let comp: AttributeClass;
    let attributeServiceStub: SinonStubbedInstance<AttributeService>;

    beforeEach(() => {
      attributeServiceStub = sinon.createStubInstance<AttributeService>(AttributeService);

      wrapper = shallowMount<AttributeClass>(AttributeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          attributeService: () => attributeServiceStub,
          alertService: () => new AlertService(),

          productMainAttributeService: () =>
            sinon.createStubInstance<ProductMainAttributeService>(ProductMainAttributeService, {
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
        comp.attribute = entity;
        attributeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attributeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.attribute = entity;
        attributeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attributeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAttribute = { id: 123 };
        attributeServiceStub.find.resolves(foundAttribute);
        attributeServiceStub.retrieve.resolves([foundAttribute]);

        // WHEN
        comp.beforeRouteEnter({ params: { attributeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.attribute).toBe(foundAttribute);
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
