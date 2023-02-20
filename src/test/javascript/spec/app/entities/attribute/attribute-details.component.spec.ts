/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AttributeDetailComponent from '@/entities/attribute/attribute-details.vue';
import AttributeClass from '@/entities/attribute/attribute-details.component';
import AttributeService from '@/entities/attribute/attribute.service';
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
  describe('Attribute Management Detail Component', () => {
    let wrapper: Wrapper<AttributeClass>;
    let comp: AttributeClass;
    let attributeServiceStub: SinonStubbedInstance<AttributeService>;

    beforeEach(() => {
      attributeServiceStub = sinon.createStubInstance<AttributeService>(AttributeService);

      wrapper = shallowMount<AttributeClass>(AttributeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { attributeService: () => attributeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAttribute = { id: 123 };
        attributeServiceStub.find.resolves(foundAttribute);

        // WHEN
        comp.retrieveAttribute(123);
        await comp.$nextTick();

        // THEN
        expect(comp.attribute).toBe(foundAttribute);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAttribute = { id: 123 };
        attributeServiceStub.find.resolves(foundAttribute);

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
