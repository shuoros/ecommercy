/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProductMainAttributeDetailComponent from '@/entities/product-main-attribute/product-main-attribute-details.vue';
import ProductMainAttributeClass from '@/entities/product-main-attribute/product-main-attribute-details.component';
import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProductMainAttribute Management Detail Component', () => {
    let wrapper: Wrapper<ProductMainAttributeClass>;
    let comp: ProductMainAttributeClass;
    let productMainAttributeServiceStub: SinonStubbedInstance<ProductMainAttributeService>;

    beforeEach(() => {
      productMainAttributeServiceStub = sinon.createStubInstance<ProductMainAttributeService>(ProductMainAttributeService);

      wrapper = shallowMount<ProductMainAttributeClass>(ProductMainAttributeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { productMainAttributeService: () => productMainAttributeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProductMainAttribute = { id: 123 };
        productMainAttributeServiceStub.find.resolves(foundProductMainAttribute);

        // WHEN
        comp.retrieveProductMainAttribute(123);
        await comp.$nextTick();

        // THEN
        expect(comp.productMainAttribute).toBe(foundProductMainAttribute);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProductMainAttribute = { id: 123 };
        productMainAttributeServiceStub.find.resolves(foundProductMainAttribute);

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
